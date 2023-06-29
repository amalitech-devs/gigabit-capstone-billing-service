package com.gigacapstone.billingservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacapstone.billingservice.dto.SubscriptionDTO;
import com.gigacapstone.billingservice.enums.BillingType;
import com.gigacapstone.billingservice.enums.ExpirationRate;
import com.gigacapstone.billingservice.exception.NotFoundException;
import com.gigacapstone.billingservice.exception.OperationFailedException;
import com.gigacapstone.billingservice.model.*;
import com.gigacapstone.billingservice.repository.BundlePackageRepository;
import com.gigacapstone.billingservice.repository.InternetTariffPlanRepository;
import com.gigacapstone.billingservice.repository.SubscriptionRepository;
import com.gigacapstone.billingservice.repository.VoicePackageRepository;
import com.gigacapstone.billingservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final BundlePackageRepository bundlePackageRepository;
    private final VoicePackageRepository voicePackageRepository;
    private final InternetTariffPlanRepository internetPackageRepository;
    private final InternetTariffPlanRepository tariffPlanRepository;
    private final ObjectMapper mapper;

    private static final String EXPIRED_STATUS = "expired";
    private static final String CURRENT_STATUS = "current";
    private static final String CANCELLED_STATUS = "cancelled";

    @Override
    public SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO) {
        TariffPlan tariffPlan = getTariffPlan(subscriptionDTO);
        LocalDate expiryDate = getExpiryDate(tariffPlan.getExpirationRate());


        Subscription subscription = mapper.convertValue(subscriptionDTO, Subscription.class);
        subscription.setExpiryDate(expiryDate);
        subscription.setStatus(CURRENT_STATUS);

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return mapper.convertValue(savedSubscription, SubscriptionDTO.class);
    }

    @Override
    public Page<SubscriptionDTO> getAllSubscriptionsOfUser(UUID userId, Pageable pageable) {
        Page<Subscription> subscriptions = subscriptionRepository.findAllByUserId(userId, pageable);
        setStatusOfSubscriptions(subscriptions);
        Page<SubscriptionDTO> pageOfSubscriptions = subscriptions.map(subscription -> mapper.convertValue(subscription, SubscriptionDTO.class));

        for (SubscriptionDTO subscription : pageOfSubscriptions) {
            attachTariffDetailsToSubscription(subscription);
        }
        return pageOfSubscriptions;
    }

    @Override
    public void cancelSubscription(UUID id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));
        if (subscription.getBillingType() == BillingType.AUTO_RENEWAL && LocalDate.now().isBefore(subscription.getExpiryDate())) {
            subscription.setStatus(CANCELLED_STATUS);
            subscriptionRepository.save(subscription);
        } else {
            throw new OperationFailedException("failed to cancel subscription");
        }

    }

    @Override
    public Page<SubscriptionDTO> searchSubscriptionsByName(UUID id, String name, Pageable pageable) {
        name = name.toLowerCase();
        Page<SubscriptionDTO> pageOfSubscriptions = subscriptionRepository.searchForUserSubscription(id, name, pageable)
                .map(subscription -> mapper.convertValue(subscription, SubscriptionDTO.class));

        for (SubscriptionDTO subscription : pageOfSubscriptions) {
            attachTariffDetailsToSubscription(subscription);
        }
        return pageOfSubscriptions;
    }

    @Override
    public void deleteSubscription(UUID id) {
        subscriptionRepository.deleteById(id);
    }

    private LocalDate getExpiryDate(ExpirationRate expirationRate) {
        LocalDate currentDate = LocalDate.now();
        return switch (expirationRate) {
            case ONE_WEEK -> currentDate.plusWeeks(1);
            case TWO_WEEKS -> currentDate.plusWeeks(2);
            case ONE_MONTH -> currentDate.plusMonths(1);
            case ONE_YEAR -> currentDate.plusYears(1);
            case PERMANENT -> currentDate.plusYears(100);
        };

    }

    private void setStatusOfSubscriptions(Page<Subscription> subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription.getExpiryDate().isBefore(LocalDate.now())) {
                subscription.setStatus(EXPIRED_STATUS);
                subscriptionRepository.save(subscription);
            }
        }
    }

    private TariffPlan getTariffPlan(SubscriptionDTO subscriptionDTO) {
        String tariffName = subscriptionDTO.getTariffName();
        return switch (subscriptionDTO.getType()) {
            case VOICE -> voicePackageRepository.findVoicePackageByName(tariffName)
                    .orElseThrow(() -> new NotFoundException("No voice package found with the name: " + tariffName));
            case BUNDLE -> bundlePackageRepository.findBundlePackageByName(tariffName)
                    .orElseThrow(() -> new NotFoundException("No bundle package found with the name: " + tariffName));
            case INTERNET -> tariffPlanRepository.findByTariffPlanName(tariffName)
                    .orElseThrow(() -> new NotFoundException("No internet package found with the name: " + tariffName))
                    .getTariffPlan();
        };
    }

    private void attachTariffDetailsToSubscription(SubscriptionDTO subscriptionDTO) {

        switch (subscriptionDTO.getType()) {
            case BUNDLE -> {
                BundlePackage bundlePackage = bundlePackageRepository.findBundlePackageByName(subscriptionDTO.getTariffName())
                        .orElseThrow(() -> new NotFoundException("No bundle package found"));
                subscriptionDTO.setDataSize(bundlePackage.getDataSize());
                subscriptionDTO.setDownloadSpeed(bundlePackage.getDownloadSpeed());
                subscriptionDTO.setUploadSpeed(bundlePackage.getUploadSpeed());
                subscriptionDTO.setCallTime(bundlePackage.getCallTime());
            }
            case INTERNET -> {
                InternetPackage internetPackage = internetPackageRepository.findByTariffPlanName(subscriptionDTO.getTariffName())
                        .orElseThrow(() -> new NotFoundException("No internet package found"));

                subscriptionDTO.setDataSize(internetPackage.getDataSize());
                subscriptionDTO.setDownloadSpeed(internetPackage.getDownloadSpeed());
                subscriptionDTO.setUploadSpeed(internetPackage.getUploadSpeed());
            }
            case VOICE -> {
                VoicePackage voicePackage = voicePackageRepository.findVoicePackageByName(subscriptionDTO.getTariffName())
                        .orElseThrow(() -> new NotFoundException("No voice package found"));
                subscriptionDTO.setCallTime(voicePackage.getCallTime());
            }
        }
    }
}
