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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SubscriptionServiceImplTest {

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private BundlePackageRepository bundlePackageRepository;
    private BundlePackage bundlePackage;
    private SubscriptionDTO subscriptionDTO;
    private Subscription subscription;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        TariffPlan tariffPlan = new TariffPlan();
        tariffPlan.setId(5);
        tariffPlan.setName("Sika Kokoo");
        tariffPlan.setExpirationRate(ExpirationRate.ONE_MONTH);

        bundlePackage = new BundlePackage();
        bundlePackage.setName("Sika Kokoo");
        bundlePackage.setExpirationRate(ExpirationRate.ONE_MONTH);
        bundlePackage.setPrice(2.00);

        subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setTariffName("Sika Kokoo");
        subscriptionDTO.setType(TariffType.BUNDLE);
        subscriptionDTO.setExpiryDate(LocalDate.now().plusMonths(1));
        subscriptionDTO.setBillingType(BillingType.AUTO_RENEWAL);
        subscriptionDTO.setPrice(2.00);


        subscription = new Subscription();
        subscription.setTariffName("Sika Kokoo");
        subscription.setType(TariffType.BUNDLE);
        subscription.setExpiryDate(LocalDate.now().plusMonths(1));
        subscription.setBillingType(BillingType.AUTO_RENEWAL);

    }

    @Test
    @DisplayName("Create_Bundle_Subscription_Success")
    void testCreateBundleSubscription_pass_successfully(){

        Mockito.when(bundlePackageRepository.findBundlePackageByName(any())).thenReturn(Optional.of(bundlePackage));
        Mockito.when(mapper.convertValue(any(), eq(Subscription.class))).thenReturn(subscription);
        Mockito.when(subscriptionRepository.save(any())).thenReturn(subscription);
        Mockito.when(mapper.convertValue(any(), eq(SubscriptionDTO.class))).thenReturn(subscriptionDTO);

        //Act
        SubscriptionDTO result = subscriptionService.createSubscription(subscriptionDTO);

        assertNotNull(result);
        assertEquals(subscriptionDTO.getType(), result.getType());
        assertEquals(subscriptionDTO.getTariffName(), result.getTariffName());

        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    @DisplayName("Get_All_Subscriptions_of_User")
    void testGetAllSubscriptionsOfUser(){
        UUID userId = UUID.randomUUID();
        Pageable pageable = Pageable.unpaged();

        //Subscription List
        List<Subscription> subscriptionList = new ArrayList<>();
        subscriptionList.add(subscription);

        //Mock the subscriptionRepository to return the subscriptionList
        Page<Subscription> subscriptions = new PageImpl<>(subscriptionList);
        when(subscriptionRepository.findSubscriptionsByUserId(userId, pageable)).thenReturn(subscriptions);

        when(mapper.convertValue(any(), eq(SubscriptionDTO.class))).thenReturn(subscriptionDTO);
        when(bundlePackageRepository.findBundlePackageByName(any())).thenReturn(Optional.of(bundlePackage));

        //Act
        Page<SubscriptionDTO> result = subscriptionService.getAllSubscriptionsOfUser(userId, pageable);

        assertNotNull(result);
        assertEquals(subscriptionDTO, result.getContent().get(0));

        verify(subscriptionRepository, times(1)).findSubscriptionsByUserId(userId, pageable);
        verify(mapper, times(1)).convertValue(subscription, SubscriptionDTO.class);
    }

    @Test
    @DisplayName("Cancel_Subscription_Success")
    void testCancelSubscription(){
        UUID subscriptionId = UUID.randomUUID();

        // Create a mock Subscription object
        Subscription subscription = new Subscription();
        subscription.setId(subscriptionId);
        subscription.setBillingType(BillingType.AUTO_RENEWAL);
        subscription.setExpiryDate(LocalDate.now().plusDays(1));

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));

        assertDoesNotThrow(() -> subscriptionService.cancelSubscription(subscriptionId));

        // Verify the interactions and assertions
        verify(subscriptionRepository, times(1)).findById(subscriptionId);
        verify(subscriptionRepository, times(1)).save(subscription);
        assertEquals("cancelled", subscription.getStatus());
    }

    @Test
    @DisplayName("Cancel_Subscription_Not_AutoRenewal")
    void testCancelSubscription_NotAutoRenewal(){
        UUID subscriptionId = UUID.randomUUID();

        // Create a mock Subscription object
        Subscription subscription = new Subscription();
        subscription.setId(subscriptionId);
        subscription.setBillingType(BillingType.ONE_TIME);
        subscription.setExpiryDate(LocalDate.now().plusDays(1));

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));

        assertThrows(OperationFailedException.class,() -> subscriptionService.cancelSubscription(subscriptionId));

        // Verify the interactions and assertions
        verify(subscriptionRepository, times(1)).findById(subscriptionId);
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Cancel_Subscription_Past_Expiry_Date")
    void testCancelSubscription_PastExpiryDate(){
        UUID subscriptionId = UUID.randomUUID();

        // Create a mock Subscription object
        Subscription subscription = new Subscription();
        subscription.setId(subscriptionId);
        subscription.setBillingType(BillingType.AUTO_RENEWAL);
        subscription.setExpiryDate(LocalDate.now().minusDays(1));

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));

        assertThrows(OperationFailedException.class,() -> subscriptionService.cancelSubscription(subscriptionId));

        // Verify the interactions and assertions
        verify(subscriptionRepository, times(1)).findById(subscriptionId);
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Cancel_Subscription_Subscription_Not_Found")
    void testCancelSubscription_SubscriptionNotFound(){
        UUID subscriptionId = UUID.randomUUID();

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> subscriptionService.cancelSubscription(subscriptionId));

        // Verify the interactions and assertions
        verify(subscriptionRepository, times(1)).findById(subscriptionId);
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Search_for_User_Subs_By_TariffName")
    void testSearchSubscriptionOfUserByTariffName(){
        UUID userId = UUID.randomUUID();
        String tariffName = "Sika Kokoo";
        Pageable pageable = Pageable.unpaged();

        List<Subscription> listOfSubscriptions = new ArrayList<>();
        listOfSubscriptions.add(subscription);

        Page<Subscription> subscriptions = new PageImpl<>(listOfSubscriptions);
        when(subscriptionRepository.searchForUserSubscription(userId, tariffName.toLowerCase(), pageable)).thenReturn(subscriptions);

        when(bundlePackageRepository.findBundlePackageByName(any())).thenReturn(Optional.of(bundlePackage));
        when(mapper.convertValue(any(), eq(SubscriptionDTO.class))).thenReturn(subscriptionDTO);

        Page<SubscriptionDTO> result = subscriptionService.searchSubscriptionsByName(userId, tariffName, pageable);

        assertNotNull(result);
        assertEquals(subscriptions.getSize(), result.getContent().size());

        verify(subscriptionRepository, times(1)).searchForUserSubscription(userId, tariffName.toLowerCase(),pageable);
    }

    @Test
    @DisplayName("Delete_Subscription_By_Id")
    void testDeleteSubscriptionById(){
        UUID subscriptionId = UUID.randomUUID();

        subscription.setId(subscriptionId);

        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));

        subscriptionService.deleteSubscription(subscriptionId);

        verify(subscriptionRepository, times(1)).deleteById(subscriptionId);
    }
}