package com.gigacapstone.billingservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacapstone.billingservice.dto.SubscriptionDTO;
import com.gigacapstone.billingservice.enums.ExpirationRate;
import com.gigacapstone.billingservice.exception.NotFoundException;
import com.gigacapstone.billingservice.model.Subscription;
import com.gigacapstone.billingservice.model.TariffPlan;
import com.gigacapstone.billingservice.repository.SubscriptionRepository;
import com.gigacapstone.billingservice.repository.TariffRepository;
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
    private final ObjectMapper mapper;
    private final TariffRepository tariffRepository;

    private static final String EXPIRED_STATUS = "expired";
    private static final String CURRENT_STATUS = "current";
    @Override
    public SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO) {
        TariffPlan tariffPlan = tariffRepository.findTariffPlanByName(subscriptionDTO.getTariffName())
                .orElseThrow(() -> new NotFoundException("tariff plan not found"));
        LocalDate expiryDate = getExpiryDate(tariffPlan);
        subscriptionDTO.setExpiryDate(expiryDate);

        Subscription subscription = mapper.convertValue(subscriptionDTO, Subscription.class);
        subscription.setStatus(CURRENT_STATUS);
        subscriptionRepository.save(subscription);
        return subscriptionDTO;
    }

    @Override
    public List<SubscriptionDTO> getAllSubscriptionsOfUser(UUID userId) {
        List<Subscription> subscriptions = subscriptionRepository.findAllByUserId(userId);
        setStatusOfSubscriptions(subscriptions);
        return subscriptions.stream()
                .map(subscription -> mapper.convertValue(subscription, SubscriptionDTO.class))
                .toList();
    }

    private LocalDate getExpiryDate(TariffPlan tariffPlan){
        if (tariffPlan.getExpirationRate() == ExpirationRate.ONE_WEEK){
            return LocalDate.now().plusWeeks(1);
        } else if (tariffPlan.getExpirationRate() == ExpirationRate.TWO_WEEKS) {
            return LocalDate.now().plusWeeks(2);
        } else if (tariffPlan.getExpirationRate() == ExpirationRate.ONE_MONTH) {
            return LocalDate.now().plusMonths(1);
        } else if (tariffPlan.getExpirationRate() == ExpirationRate.ONE_YEAR) {
            return LocalDate.now().plusYears(1);
        } else if (tariffPlan.getExpirationRate() == ExpirationRate.PERMANENT) {
            return LocalDate.now().plusYears(100);
        }
        return LocalDate.now();
    }

    private void setStatusOfSubscriptions(List<Subscription> subscriptions){
        for(Subscription subscription : subscriptions){
            if(subscription.getExpiryDate().isBefore(LocalDate.now())){
                subscription.setStatus(EXPIRED_STATUS);
            }
            subscription.setStatus(CURRENT_STATUS);
        }
    }
}