package com.gigacapstone.billingservice.service;

import com.gigacapstone.billingservice.dto.SubscriptionDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {

    SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO);

    void deleteSubscription(UUID id);

    List<SubscriptionDTO> getAllSubscriptionsOfUser(UUID userId, Pageable pageable);

    void cancelSubscription(UUID id);

}
