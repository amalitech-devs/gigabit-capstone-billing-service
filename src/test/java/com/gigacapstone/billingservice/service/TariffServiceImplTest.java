package com.gigacapstone.billingservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacapstone.billingservice.dto.AllPackagesDTO;
import com.gigacapstone.billingservice.dto.BundlePackageDTO;
import com.gigacapstone.billingservice.dto.VoicePackageDTO;
import com.gigacapstone.billingservice.exception.EntityAlreadyExistException;
import com.gigacapstone.billingservice.model.BundlePackage;
import com.gigacapstone.billingservice.model.InternetPackage;
import com.gigacapstone.billingservice.model.VoicePackage;
import com.gigacapstone.billingservice.repository.BundlePackageRepository;
import com.gigacapstone.billingservice.repository.InternetTariffPlanRepository;
import com.gigacapstone.billingservice.repository.TariffRepository;
import com.gigacapstone.billingservice.repository.VoicePackageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TariffServiceImplTest {

    @Mock
    private TariffRepository tariffRepository;

    @Mock
    private VoicePackageRepository voicePackageRepository;

    @Mock
    private BundlePackageRepository bundlePackageRepository;

    @Mock
    private InternetTariffPlanRepository internetPackageRepository;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private TariffServiceImpl tariffService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createVoicePackage_WithValidPackage_ShouldReturnCreatedPackage() {
        // Arrange
        VoicePackageDTO voicePackageDTO = new VoicePackageDTO();
        voicePackageDTO.setName("Package A");
        VoicePackage voicePackage = new VoicePackage();
        voicePackage.setName("Package A");

        when(voicePackageRepository.findVoicePackageByName(anyString())).thenReturn(Optional.empty());
        when(tariffRepository.save(any(VoicePackage.class))).thenReturn(voicePackage);
        when(mapper.convertValue(any(VoicePackageDTO.class), eq(VoicePackage.class))).thenReturn(voicePackage);

        // Act
        VoicePackageDTO createdPackage = tariffService.createVoicePackage(voicePackageDTO);

        // Assert
        Assertions.assertEquals(voicePackageDTO.getName(), createdPackage.getName());
        verify(voicePackageRepository, times(1)).findVoicePackageByName(anyString());
        verify(tariffRepository, times(1)).save(any(VoicePackage.class));
    }

    @Test
    void createVoicePackage_WithExistingPackage_ShouldThrowEntityAlreadyExistException() {
        // Arrange
        VoicePackageDTO voicePackageDTO = new VoicePackageDTO();
        voicePackageDTO.setName("Package A");
        VoicePackage existingPackage = new VoicePackage();
        existingPackage.setName("Package A");

        when(voicePackageRepository.findVoicePackageByName(anyString())).thenReturn(Optional.of(existingPackage));

        // Act and Assert
        assertThrows(EntityAlreadyExistException.class, () -> {
            tariffService.createVoicePackage(voicePackageDTO);
        });
        verify(voicePackageRepository, times(1)).findVoicePackageByName(anyString());
        verify(tariffRepository, never()).save(any(VoicePackage.class));
    }

    @Test
    void createBundlePackage_WithValidPackage_ShouldReturnCreatedPackage() {
        // Arrange
        BundlePackageDTO bundlePackageDTO = new BundlePackageDTO();
        bundlePackageDTO.setName("Package X");
        BundlePackage bundlePackage = new BundlePackage();
        bundlePackage.setName("Package X");

        when(bundlePackageRepository.findBundlePackageByName(anyString())).thenReturn(Optional.empty());
        when(tariffRepository.save(any(BundlePackage.class))).thenReturn(bundlePackage);
        when(mapper.convertValue(any(BundlePackageDTO.class), eq(BundlePackage.class))).thenReturn(bundlePackage);

        // Act
        BundlePackageDTO createdPackage = tariffService.createBundlePackage(bundlePackageDTO);

        // Assert
        Assertions.assertEquals(bundlePackageDTO.getName(), createdPackage.getName());
        verify(bundlePackageRepository, times(1)).findBundlePackageByName(anyString());
        verify(tariffRepository, times(1)).save(any(BundlePackage.class));
    }

    @Test
    void createBundlePackage_WithExistingPackage_ShouldThrowEntityAlreadyExistException() {
        // Arrange
        BundlePackageDTO bundlePackageDTO = new BundlePackageDTO();
        bundlePackageDTO.setName("Package X");
        BundlePackage existingPackage = new BundlePackage();
        existingPackage.setName("Package X");

        when(bundlePackageRepository.findBundlePackageByName(anyString())).thenReturn(Optional.of(existingPackage));

        // Act and Assert
        assertThrows(EntityAlreadyExistException.class, () -> {
            tariffService.createBundlePackage(bundlePackageDTO);
        });
        verify(bundlePackageRepository, times(1)).findBundlePackageByName(anyString());
        verify(tariffRepository, never()).save(any(BundlePackage.class));
    }

    @Test
    void listAllBundlePackages_success(){
        BundlePackage package1 = new BundlePackage();
        package1.setName("Package 1");
        BundlePackage package2 = new BundlePackage();
        package2.setName("Package 2");

        List<BundlePackage> bundles = List.of(package1, package2);
        BundlePackageDTO bundlePackageDTO1 = new BundlePackageDTO();
        bundlePackageDTO1.setName("Package 1");

        BundlePackageDTO bundlePackageDTO2 = new BundlePackageDTO();
        bundlePackageDTO2.setName("Package 2");

        Page<BundlePackage> pageOfBundles = new PageImpl<>(bundles);

        when(bundlePackageRepository.findAll(any(Pageable.class))).thenReturn(pageOfBundles);
        when(mapper.convertValue(any(BundlePackage.class), eq(BundlePackageDTO.class))).thenReturn(bundlePackageDTO1, bundlePackageDTO2);

        Page<BundlePackageDTO> result = tariffService.listAllBundlePackages(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(2, result.getSize());

        verify(bundlePackageRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void listAllBundlePackages_empty(){
        Pageable pageable = Pageable.unpaged();

        when(bundlePackageRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        Page<BundlePackageDTO> result = tariffService.listAllBundlePackages(pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void listAllVoicePackages_success(){
        VoicePackage package1 = new VoicePackage();
        package1.setName("Package 1");
        VoicePackage package2 = new VoicePackage();
        package2.setName("Package 2");

        List<VoicePackage> bundles = List.of(package1, package2);
        VoicePackageDTO voicePackageDto1 = new VoicePackageDTO();
        voicePackageDto1.setName("Package 1");

        VoicePackageDTO voicePackageDto2 = new VoicePackageDTO();
        voicePackageDto2.setName("Package 2");

        Page<VoicePackage> pageOfBundles = new PageImpl<>(bundles);

        when(voicePackageRepository.findAll(any(Pageable.class))).thenReturn(pageOfBundles);
        when(mapper.convertValue(any(VoicePackage.class), eq(VoicePackageDTO.class))).thenReturn(voicePackageDto1, voicePackageDto2);

        Page<VoicePackageDTO> result = tariffService.listAllVoicePackages(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(2, result.getSize());

        verify(voicePackageRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void listAllVoicePackages_empty(){
        Pageable pageable = Pageable.unpaged();

        when(voicePackageRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        Page<VoicePackageDTO> result = tariffService.listAllVoicePackages(pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void listAllPackages_success(){
        VoicePackage voicePackage = new VoicePackage();
        voicePackage.setName("Voice Package");
        BundlePackage bundlePackage = new BundlePackage();
        bundlePackage.setName("Bundle Package");

        VoicePackageDTO voicePackageDto = new VoicePackageDTO();
        voicePackageDto.setName("Voice Package");
        BundlePackageDTO bundlePackageDto = new BundlePackageDTO();
        bundlePackageDto.setName("Bundle Package");

        when(tariffRepository.findVoicePackages()).thenReturn(List.of(voicePackage));
        when(tariffRepository.findBundlePackages()).thenReturn(List.of(bundlePackage));
        when(internetPackageRepository.findAll()).thenReturn(Collections.emptyList());

        when(mapper.convertValue(any(), eq(VoicePackageDTO.class))).thenReturn(voicePackageDto);
        when(mapper.convertValue(any(), eq(BundlePackageDTO.class))).thenReturn(bundlePackageDto);

        AllPackagesDTO result = tariffService.listAllPackages();

        assertNotNull(result);
        assertEquals(1, result.getVoicePackages().size());
        assertEquals(1, result.getBundlePackages().size());
        assertEquals(0 ,result.getInternetPackages().size());
    }

    @Test
    void searchVoicePackageByNameSuccess(){
        String voicePackageName = "Sika Kasa";
        Pageable pageable = Pageable.unpaged();

        VoicePackage voicePackage = new VoicePackage();
        voicePackage.setName(voicePackageName);

        Page<VoicePackage> pageOfVoicePackage = new PageImpl<>(List.of(voicePackage));

        when(tariffRepository.searchVoicePackages(anyString(), eq(pageable))).thenReturn(pageOfVoicePackage);

        Page<VoicePackageDTO> result = tariffService.searchVoicePackage(voicePackageName, pageable);

        assertNotNull(result);
        assertEquals(pageOfVoicePackage.getSize(), result.getSize());

        verify(tariffRepository, times(1)).searchVoicePackages(anyString(), eq(pageable));
    }

    @Test
    void searchVoicePackageByNameEmptyPage(){
        String voicePackageName = "Sika Kasa";
        Pageable pageable = Pageable.unpaged();

        Page<VoicePackage> emptyPageOfVoicePackages = new PageImpl<>(Collections.emptyList());

        when(tariffRepository.searchVoicePackages(anyString(), eq(pageable))).thenReturn(emptyPageOfVoicePackages);

        Page<VoicePackageDTO> result = tariffService.searchVoicePackage(voicePackageName, pageable);

        assertNotNull(result);
        assertEquals(emptyPageOfVoicePackages.getSize(), result.getSize());

        verify(tariffRepository, times(1)).searchVoicePackages(anyString(), eq(pageable));
    }

    @Test
    void searchBundlePackageByNameSuccess(){
        String bundlePackageName = "Sika Kasa";
        Pageable pageable = Pageable.unpaged();

        BundlePackage bundlePackage = new BundlePackage();
        bundlePackage.setName(bundlePackageName);

        Page<BundlePackage> pageOfBundlePackage = new PageImpl<>(List.of(bundlePackage));

        when(tariffRepository.searchBundlePackages(anyString(), eq(pageable))).thenReturn(pageOfBundlePackage);

        Page<BundlePackageDTO> result = tariffService.searchBundlePackage(bundlePackageName, pageable);

        assertNotNull(result);
        assertEquals(pageOfBundlePackage.getSize(), result.getSize());

        verify(tariffRepository, times(1)).searchBundlePackages(anyString(), eq(pageable));
    }

    @Test
    void searchBundlePackageByNameEmptyPage(){
        String voicePackageName = "Sika Kasa";
        Pageable pageable = Pageable.unpaged();

        Page<BundlePackage> emptyPageOfBundlePackages = new PageImpl<>(Collections.emptyList());

        when(tariffRepository.searchBundlePackages(anyString(), eq(pageable))).thenReturn(emptyPageOfBundlePackages);

        Page<BundlePackageDTO> result = tariffService.searchBundlePackage(voicePackageName, pageable);

        assertNotNull(result);
        assertEquals(emptyPageOfBundlePackages.getSize(), result.getSize());

        verify(tariffRepository, times(1)).searchBundlePackages(anyString(), eq(pageable));
    }
}
