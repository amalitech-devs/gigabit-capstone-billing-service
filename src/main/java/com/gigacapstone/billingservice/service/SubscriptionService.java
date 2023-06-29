package com.gigacapstone.billingservice.service;

import com.gigacapstone.billingservice.dto.SubscriptionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SubscriptionService {

    SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO);

    void deleteSubscription(UUID id);

    Page<SubscriptionDTO> getAllSubscriptionsOfUser(UUID userId, Pageable pageable);

    void cancelSubscription(UUID id);

    Page<SubscriptionDTO> searchSubscriptionsByName(UUID id,String name, Pageable pageable);

}
