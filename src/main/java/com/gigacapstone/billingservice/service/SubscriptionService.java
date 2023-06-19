package com.gigacapstone.billingservice.service;

import com.gigacapstone.billingservice.dto.SubscriptionDTO;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {

    SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO);

    List<SubscriptionDTO> getAllSubscriptionsOfUser(UUID userId);
}
