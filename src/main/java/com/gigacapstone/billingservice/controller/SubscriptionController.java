package com.gigacapstone.billingservice.controller;

import com.gigacapstone.billingservice.dto.SubscriptionDTO;
import com.gigacapstone.billingservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/{userId}")
    ResponseEntity<List<SubscriptionDTO>> getAllSubscriptionForUser(@PathVariable UUID userId, @RequestParam("type")Optional<String> type, Pageable pageable){
        return ResponseEntity
                .ok(subscriptionService.getAllSubscriptionsOfUser(userId,type, pageable));
    }

    @PostMapping
    ResponseEntity<SubscriptionDTO> createSubscription(@RequestBody SubscriptionDTO subscriptionDTO){
        return ResponseEntity.status(HttpStatus.SC_CREATED)
                .body(subscriptionService.createSubscription(subscriptionDTO));
    }
}
