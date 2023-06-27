package com.gigacapstone.billingservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacapstone.billingservice.dto.SubscriptionDTO;
import com.gigacapstone.billingservice.enums.ExpirationRate;
import com.gigacapstone.billingservice.enums.TariffType;
import com.gigacapstone.billingservice.exception.NotFoundException;
import com.gigacapstone.billingservice.model.Subscription;
import com.gigacapstone.billingservice.model.TariffPlan;
import com.gigacapstone.billingservice.repository.*;
import com.gigacapstone.billingservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final BundlePackageRepository bundlePackageRepository;
    private final VoicePackageRepository voicePackageRepository;
    private final InternetTariffPlanRepository tariffPlanRepository;
    private final ObjectMapper mapper;

    private static final String EXPIRED_STATUS = "expired";
    private static final String CURRENT_STATUS = "current";
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
    public List<SubscriptionDTO> getAllSubscriptionsOfUser(UUID userId) {
        List<Subscription> subscriptions = subscriptionRepository.findAllByUserId(userId);
        setStatusOfSubscriptions(subscriptions);
        return subscriptions.stream()
                .map(subscription -> mapper.convertValue(subscription, SubscriptionDTO.class))
                .toList();
    }

    @Override
    public void deleteSubscription(UUID id) {
        subscriptionRepository.deleteById(id);
    }

    private LocalDate getExpiryDate(ExpirationRate expirationRate){
        if (expirationRate == ExpirationRate.ONE_WEEK){
            return LocalDate.now().plusWeeks(1);
        } else if (expirationRate == ExpirationRate.TWO_WEEKS) {
            return LocalDate.now().plusWeeks(2);
        } else if (expirationRate == ExpirationRate.ONE_MONTH) {
            return LocalDate.now().plusMonths(1);
        } else if (expirationRate == ExpirationRate.ONE_YEAR) {
            return LocalDate.now().plusYears(1);
        } else if (expirationRate == ExpirationRate.PERMANENT) {
            return LocalDate.now().plusYears(100);
        }
        return LocalDate.now();
    }

    private void setStatusOfSubscriptions(List<Subscription> subscriptions){
        for(Subscription subscription : subscriptions){
            if(subscription.getExpiryDate().isBefore(LocalDate.now())){
                subscription.setStatus(EXPIRED_STATUS);
                continue;
            }
            subscription.setStatus(CURRENT_STATUS);
        }
    }

    private TariffPlan getTariffPlan(SubscriptionDTO subscriptionDTO){
        if (subscriptionDTO.getType() == TariffType.VOICE){
            return  voicePackageRepository.findVoicePackageByName(subscriptionDTO.getTariffName())
                    .orElseThrow(() -> new NotFoundException("no voice package found with such name"));
        } else if (subscriptionDTO.getType() == TariffType.BUNDLE) {
            return bundlePackageRepository.findBundlePackageByName(subscriptionDTO.getTariffName())
                    .orElseThrow(() -> new NotFoundException("no bundle package found with such name"));
        } else if (subscriptionDTO.getType() == TariffType.INTERNET) {
            return tariffPlanRepository.findByTariffPlanName(subscriptionDTO.getTariffName())
                    .orElseThrow(() -> new NotFoundException("no internet package found with such name"))
                    .getTariffPlan();
        }

        throw new NotFoundException("No package found with such name");
    }
}
