package com.gigacapstone.billingservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacapstone.billingservice.dto.SubscriptionDTO;
import com.gigacapstone.billingservice.enums.ExpirationRate;
import com.gigacapstone.billingservice.exception.NotFoundException;
import com.gigacapstone.billingservice.model.Subscription;
import com.gigacapstone.billingservice.model.TariffPlan;
import com.gigacapstone.billingservice.repository.*;
import com.gigacapstone.billingservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    private final InternetTariffPlanRepository tariffPlanRepository;
    private final ObjectMapper mapper;

    private static final String EXPIRED_STATUS = "expired";
    private static final String CURRENT_STATUS = "current";
    @Override
    public SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO) {
        TariffPlan tariffPlan = getTariffPlan(subscriptionDTO);
        LocalDate expiryDate = getExpiryDate(tariffPlan.getExpirationRate());
        subscriptionDTO.setExpiryDate(expiryDate);

        Subscription subscription = mapper.convertValue(subscriptionDTO, Subscription.class);
        subscription.setStatus(CURRENT_STATUS);
        subscription.setCreatedAt(new Timestamp(new Date().getTime()));
        Subscription save = subscriptionRepository.save(subscription);
        return mapper.convertValue(save,SubscriptionDTO.class);
    }

    @Override
    public List<SubscriptionDTO> getAllSubscriptionsOfUser(UUID userId, Optional<String> type, Pageable pageable) {
        Page<Subscription> subscriptions = subscriptionRepository.findAllByUserId(userId,pageable);
        setStatusOfSubscriptions(subscriptions);
        return subscriptions.stream()
                .map(subscription -> mapper.convertValue(subscription, SubscriptionDTO.class)).filter(v->(type.isEmpty() || v.getType().name().equalsIgnoreCase(type.get())))
                .toList();
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
    private void setStatusOfSubscriptions(Page<Subscription> subscriptions){
        for(Subscription subscription : subscriptions){
            if(subscription.getExpiryDate().isBefore(LocalDate.now())){
                subscription.setStatus(EXPIRED_STATUS);
                continue;
            }
            subscription.setStatus(CURRENT_STATUS);
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
}
