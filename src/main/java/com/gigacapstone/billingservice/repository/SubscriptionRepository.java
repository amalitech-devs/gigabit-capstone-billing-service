package com.gigacapstone.billingservice.repository;

import com.gigacapstone.billingservice.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID>{

    Page<Subscription> findSubscriptionsByUserId(UUID userId, Pageable pageable);

    @Query("SELECT s FROM Subscription s WHERE s.userId = :id AND LOWER(s.tariffName) LIKE %:tariffName%")
    Page<Subscription> searchForUserSubscription(UUID id, String tariffName, Pageable pageable);
}
