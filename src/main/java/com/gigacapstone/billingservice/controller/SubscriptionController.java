package com.gigacapstone.billingservice.controller;

import com.gigacapstone.billingservice.dto.SubscriptionDTO;
import com.gigacapstone.billingservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/{id}")
    List<SubscriptionDTO> getAllSubscriptionForUser(@PathVariable UUID id){
        return subscriptionService.getAllSubscriptionsOfUser(id);
    }

    @PostMapping
    SubscriptionDTO createSubscription(@RequestBody SubscriptionDTO subscriptionDTO){
        return subscriptionService.createSubscription(subscriptionDTO);
    }
}
