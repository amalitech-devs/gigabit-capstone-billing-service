package com.gigacapstone.billingservice.repository;

import com.gigacapstone.billingservice.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    @Query("SELECT s FROM Subscription s WHERE s.userId = :userId")
    List<Subscription> findAllByUserId(UUID userId);
}
