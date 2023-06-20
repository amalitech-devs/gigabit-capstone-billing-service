package com.gigacapstone.billingservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacapstone.billingservice.dto.AllPackagesDTO;
import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.InternetPackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.exception.EntityAlreadyExistException;
import com.gigacapstone.billingservice.model.BundlePackage;
import com.gigacapstone.billingservice.model.InternetPackage;
import com.gigacapstone.billingservice.model.VoicePackage;
import com.gigacapstone.billingservice.repository.BundlePackageRepository;
import com.gigacapstone.billingservice.repository.InternetTariffPlanRepository;
import com.gigacapstone.billingservice.repository.TariffRepository;
import com.gigacapstone.billingservice.repository.VoicePackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;
    private final ObjectMapper mapper;
    private final InternetTariffPlanRepository internetTariffPlanRepository;
    private final VoicePackageRepository voicePackageRepository;
    private final BundlePackageRepository bundlePackageRepository;

    @Override
    public VoicePackageDTO createVoicePackage(VoicePackageDTO voicePackage) {
        if (voicePackage == null) {
            throw new IllegalArgumentException("Input voice package cannot be null");
        }
        Optional<VoicePackage> tarriffPlan = voicePackageRepository.findVoicePackageByName(voicePackage.getName());
        if (tarriffPlan.isPresent()) {
            throw new EntityAlreadyExistException("voice package already exists with such name");
        }

        voicePackage.setIsEnabled(false);
        VoicePackage theVoicePackageToBeSaved = mapper.convertValue(voicePackage, VoicePackage.class);
        tariffRepository.save(theVoicePackageToBeSaved);

        return voicePackage;
    }

    @Override
    public BundlePackageDTO createBundlePackage(BundlePackageDTO bundlePackage) {
        if (bundlePackage == null) {
            throw new IllegalArgumentException("Input voice package cannot be null");
        }

        Optional<BundlePackage> tarriffPlan = bundlePackageRepository.findBundlePackageByName(bundlePackage.getName());
        if (tarriffPlan.isPresent()) {
            throw new EntityAlreadyExistException("bundle package already exists with such name");
        }
        bundlePackage.setIsEnabled(false);
        tariffRepository.save(mapper.convertValue(bundlePackage, BundlePackage.class));

        return bundlePackage;
    }

    @Override
    public Page<BundlePackageDTO> listAllBundlePackages(Pageable pageable) {
        Page<BundlePackage> bundlePackages = tariffRepository.findBundlePackages(pageable);
        return bundlePackages.map(bundle -> mapper.convertValue(bundle, BundlePackageDTO.class));
    }

    @Override
    public Page<VoicePackageDTO> listAllVoicePackages(Pageable pageable) {
        Page<VoicePackage> voicePackages = tariffRepository.findVoicePackages(pageable);
        return voicePackages.map(voice -> mapper.convertValue(voice, VoicePackageDTO.class));
    }

    @Override
    public AllPackagesDTO listAllPackages(Pageable pageable) {
        var voicePackageFuture = CompletableFuture.supplyAsync(() -> tariffRepository.findVoicePackages(pageable));
        var internetPackageFuture = CompletableFuture.supplyAsync(() -> internetTariffPlanRepository.findAll(pageable));
        var bundlePackageFuture = CompletableFuture.supplyAsync(() -> tariffRepository.findBundlePackages(pageable));

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(voicePackageFuture, internetPackageFuture, bundlePackageFuture);

        try {
            combinedFuture.join();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not fetch packages");
        }

        Page<VoicePackage> voicePackagePage = voicePackageFuture.join();
        Page<InternetPackage> internetPackagePage = internetPackageFuture.join();
        Page<BundlePackage> bundlePackagePage = bundlePackageFuture.join();

        List<VoicePackageDTO> voicePackageDTOs = voicePackagePage.getContent().stream()
                .map(voicePackage -> mapper.convertValue(voicePackage, VoicePackageDTO.class))
                .toList();

        List<BundlePackageDTO> bundlePackageDTOs = bundlePackagePage.getContent().stream()
                .map(bundlePackage -> mapper.convertValue(bundlePackage, BundlePackageDTO.class))
                .toList();

        List<InternetPackageDTO> internetPackageDTOs = internetPackagePage.getContent().stream()
                .map(internetPackage -> mapper.convertValue(internetPackage, InternetPackageDTO.class))
                .toList();

       return new AllPackagesDTO(voicePackageDTOs, bundlePackageDTOs, internetPackageDTOs);
    }

    @Override
    public Page<VoicePackageDTO> searchVoicePackage(String packageName, Pageable pageable) {
        Page<VoicePackage> voicePackages = tariffRepository.searchVoicePackages(packageName, pageable);
        return voicePackages.map(voicePackage -> mapper.convertValue(voicePackage, VoicePackageDTO.class));
    }

    @Override
    public Page<BundlePackageDTO> searchBundlePackage(String packageName, Pageable pageable) {
        Page<BundlePackage> bundlePackages = tariffRepository.searchBundlePackages(packageName, pageable);
        return bundlePackages.map(bundlePackage -> mapper.convertValue(bundlePackage, BundlePackageDTO.class));
    }
}
