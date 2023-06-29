package com.gigacapstone.billingservice.controller;

import com.gigacapstone.billingservice.dto.SubscriptionDTO;
import com.gigacapstone.billingservice.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.data.domain.Page;
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
    ResponseEntity<Page<SubscriptionDTO>> getAllSubscriptionForUser(@PathVariable UUID userId, @RequestParam("type")Optional<String> type, Pageable pageable){
        return ResponseEntity
                .ok(subscriptionService.getAllSubscriptionsOfUser(userId, pageable));
    }

    @PostMapping
    ResponseEntity<SubscriptionDTO> createSubscription(@Valid @RequestBody SubscriptionDTO subscriptionDTO){
        return ResponseEntity.status(HttpStatus.SC_CREATED)
                .body(subscriptionService.createSubscription(subscriptionDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    void deleteSubscription(@PathVariable UUID id){
        subscriptionService.deleteSubscription(id);
    }

    @PutMapping("/{id}")
    void cancelSubscription(@PathVariable UUID id){
        subscriptionService.cancelSubscription(id);
    }

    @GetMapping("/search")
    ResponseEntity<Page<SubscriptionDTO>> searchUserSubscriptions(@RequestParam UUID id, @RequestParam String name, Pageable pageable){
        return ResponseEntity.ok(subscriptionService.searchSubscriptionsByName(id, name, pageable));
    }
}