package com.gigacapstone.billingservice.repository;

import com.gigacapstone.billingservice.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID>,
        PagingAndSortingRepository<Subscription, UUID> {

    Page<Subscription> findAllByUserId(UUID userId, Pageable pageable);
}
