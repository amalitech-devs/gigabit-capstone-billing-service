package com.gigacapstone.billingservice.controller;

import com.gigacapstone.billingservice.dto.SubscriptionDTO;
import com.gigacapstone.billingservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/{userId}")
    ResponseEntity<List<SubscriptionDTO>> getAllSubscriptionForUser(@PathVariable UUID userId){
        return ResponseEntity
                .ok(subscriptionService.getAllSubscriptionsOfUser(userId));
    }

    @PostMapping
    ResponseEntity<SubscriptionDTO> createSubscription(@RequestBody SubscriptionDTO subscriptionDTO){
        return ResponseEntity.status(HttpStatus.SC_CREATED)
                .body(subscriptionService.createSubscription(subscriptionDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    void deleteSubscription(@PathVariable UUID id){
        subscriptionService.deleteSubscription(id);
    }
}
