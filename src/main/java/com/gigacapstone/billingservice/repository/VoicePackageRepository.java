package com.gigacapstone.billingservice.repository;

import com.gigacapstone.billingservice.model.VoicePackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoicePackageRepository extends JpaRepository<VoicePackage, Integer> {

    Optional<VoicePackage> findVoicePackageByName(String name);
}
