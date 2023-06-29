package com.gigacapstone.billingservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacapstone.billingservice.dto.SubscriptionDTO;
import com.gigacapstone.billingservice.enums.BillingType;
import com.gigacapstone.billingservice.enums.ExpirationRate;
import com.gigacapstone.billingservice.enums.TariffType;
import com.gigacapstone.billingservice.exception.NotFoundException;
import com.gigacapstone.billingservice.exception.OperationFailedException;
import com.gigacapstone.billingservice.model.BundlePackage;
import com.gigacapstone.billingservice.model.Subscription;
import com.gigacapstone.billingservice.model.TariffPlan;
import com.gigacapstone.billingservice.repository.BundlePackageRepository;
import com.gigacapstone.billingservice.repository.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private BundlePackageRepository bundlePackageRepository;


    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSubscription() {
        // Mock input data
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setUserId(UUID.randomUUID());
        subscriptionDTO.setTariffName("Tariff 1");
        subscriptionDTO.setType(TariffType.BUNDLE);

        // Mock TariffPlan and its related data
        TariffPlan tariffPlan = new BundlePackage();
        tariffPlan.setExpirationRate(ExpirationRate.ONE_MONTH);

        // Mock converted Subscription object
        Subscription subscription = new Subscription();
        subscription.setTariffName(tariffPlan.getName());
        subscription.setExpiryDate(LocalDate.now().plusMonths(1));
        subscription.setStatus("current");

        // Mock saved Subscription object
        Subscription savedSubscription = new Subscription();
        savedSubscription.setId(UUID.randomUUID());

        // Mock ObjectMapper conversion
        SubscriptionDTO savedSubscriptionDTO = new SubscriptionDTO();
        savedSubscriptionDTO.setId(savedSubscription.getId());

        // Mock repository methods
        when(bundlePackageRepository.findBundlePackageByName("Tariff 1")).thenReturn(Optional.of((BundlePackage) tariffPlan));
        when(subscriptionRepository.save(subscription)).thenReturn(savedSubscription);
        when(mapper.convertValue(savedSubscription, SubscriptionDTO.class)).thenReturn(savedSubscriptionDTO);

        // Call the service method
        SubscriptionDTO resultDTO = subscriptionService.createSubscription(subscriptionDTO);

        // Verify the repository method calls
        verify(bundlePackageRepository, times(1)).findBundlePackageByName("Tariff 1");
        verify(subscriptionRepository, times(1)).save(subscription);
        verify(mapper, times(1)).convertValue(savedSubscription, SubscriptionDTO.class);

        // Verify the result
        assertNotNull(resultDTO);
        assertEquals(savedSubscription.getId(), resultDTO.getId());
    }

    @Test
    void testGetAllSubscriptionsOfUser() {
        // Mock input data
        UUID userId = UUID.randomUUID();
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        // Mock Subscription objects
        List<Subscription> subscriptionList = new ArrayList<>();
        subscriptionList.add(createSubscription(UUID.randomUUID(), userId, "Subscription 1"));
        subscriptionList.add(createSubscription(UUID.randomUUID(), userId, "Subscription 2"));

        // Mock Page object
        Page<Subscription> subscriptionPage = new PageImpl<>(subscriptionList, pageable, subscriptionList.size());

        // Mock ObjectMapper conversion
        List<SubscriptionDTO> subscriptionDTOList = new ArrayList<>();
        for (Subscription subscription : subscriptionList) {
            SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
            subscriptionDTO.setId(subscription.getId());
            subscriptionDTOList.add(subscriptionDTO);
        }
        Page<SubscriptionDTO> subscriptionDTOPage = new PageImpl<>(subscriptionDTOList, pageable, subscriptionDTOList.size());

        // Mock repository methods
        when(subscriptionRepository.findSubscriptionsByUserId(userId, pageable)).thenReturn(subscriptionPage);
        when(mapper.convertValue(any(Subscription.class), eq(SubscriptionDTO.class))).thenReturn(new SubscriptionDTO());

        // Call the service method
        Page<SubscriptionDTO> resultPage = subscriptionService.getAllSubscriptionsOfUser(userId, pageable);

        // Verify the repository method calls
        verify(subscriptionRepository, times(1)).findSubscriptionsByUserId(userId, pageable);
        verify(mapper, times(subscriptionList.size())).convertValue(any(Subscription.class), eq(SubscriptionDTO.class));

        // Verify the result
        assertNotNull(resultPage);
        assertEquals(subscriptionDTOPage.getTotalElements(), resultPage.getTotalElements());
        assertEquals(subscriptionDTOPage.getContent().size(), resultPage.getContent().size());
    }

    @Test
    void testCancelSubscription() {
        // Mock input data
        UUID subscriptionId = UUID.randomUUID();

        // Mock Subscription object
        Subscription subscription = new Subscription();
        subscription.setId(subscriptionId);
        subscription.setBillingType(BillingType.AUTO_RENEWAL);
        subscription.setExpiryDate(LocalDate.now().plusDays(1));

        // Mock repository methods
        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        // Call the service method
        subscriptionService.cancelSubscription(subscriptionId);

        // Verify the repository method calls
        verify(subscriptionRepository, times(1)).findById(subscriptionId);
        verify(subscriptionRepository, times(1)).save(subscription);

        // Verify the subscription status
        assertEquals("cancelled", subscription.getStatus());
    }

    @Test
    void testCancelSubscription_NotFoundException() {
        // Mock input data
        UUID subscriptionId = UUID.randomUUID();

        // Mock repository methods
        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        // Call the service method and verify the NotFoundException
        assertThrows(NotFoundException.class, () -> subscriptionService.cancelSubscription(subscriptionId));

        // Verify the repository method calls
        verify(subscriptionRepository, times(1)).findById(subscriptionId);
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    void testCancelSubscription_OperationFailedException() {
        // Mock input data
        UUID subscriptionId = UUID.randomUUID();

        // Mock Subscription object
        Subscription subscription = new Subscription();
        subscription.setId(subscriptionId);
        subscription.setBillingType(BillingType.AUTO_RENEWAL);
        subscription.setExpiryDate(LocalDate.now().plusDays(1));

        // Mock repository methods
        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));

        // Call the service method and verify the OperationFailedException
        assertThrows(OperationFailedException.class, () -> subscriptionService.cancelSubscription(subscriptionId));

        // Verify the repository method calls
        verify(subscriptionRepository, times(1)).findById(subscriptionId);
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    private Subscription createSubscription(UUID id, UUID userId, String tariffName) {
        Subscription subscription = new Subscription();
        subscription.setId(id);
        subscription.setUserId(userId);
        subscription.setTariffName(tariffName);
        return subscription;
    }
}
