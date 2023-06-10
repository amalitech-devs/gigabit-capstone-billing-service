package com.gigacapstone.billingservice.dto.mappers;

import static org.junit.jupiter.api.Assertions.*;
import com.gigacapstone.billingservice.dto.InternetPackageDTO;
import com.gigacapstone.billingservice.enums.ExpirationRate;
import com.gigacapstone.billingservice.model.InternetPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ModelMapperTest {

    @InjectMocks
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mapToEntityOrDto_Success() {
        // Arrange
        InternetPackageDTO sourceDto = new InternetPackageDTO();
        sourceDto.setId(UUID.randomUUID());
        sourceDto.setName("Plan A");
        sourceDto.setPrice(10.0);
        sourceDto.setDownloadSpeed(100);
        sourceDto.setUploadSpeed(50);
        sourceDto.setExpiry(ExpirationRate.ONE_YEAR);

        // Act
        InternetPackage destinationEntity = modelMapper.mapToEntityOrDto(sourceDto, InternetPackage.class);

        // Assert
        assertNotNull(destinationEntity);
        assertEquals(sourceDto.getId(), destinationEntity.getId());
        assertEquals(sourceDto.getName(), destinationEntity.getName());
        assertEquals(sourceDto.getPrice(), destinationEntity.getPrice());
        assertEquals(sourceDto.getDownloadSpeed(), destinationEntity.getDownloadSpeed());
        assertEquals(sourceDto.getUploadSpeed(), destinationEntity.getUploadSpeed());
    }


    @Test
    void mapToEntityOrDto_without_id_Success() {
        // Arrange
        InternetPackageDTO sourceDto = new InternetPackageDTO();
        sourceDto.setName("Plan A");
        sourceDto.setPrice(10.0);
        sourceDto.setDownloadSpeed(100);
        sourceDto.setUploadSpeed(50);
        sourceDto.setExpiry(ExpirationRate.ONE_MONTH);

        // Act
        InternetPackage destinationEntity = modelMapper.mapToEntityOrDto(sourceDto, InternetPackage.class);

        // Assert
        assertNotNull(destinationEntity);
        assertEquals(sourceDto.getId(), destinationEntity.getId());
        assertEquals(sourceDto.getName(), destinationEntity.getName());
        assertEquals(sourceDto.getPrice(), destinationEntity.getPrice());
        assertEquals(sourceDto.getDownloadSpeed(), destinationEntity.getDownloadSpeed());
        assertEquals(sourceDto.getUploadSpeed(), destinationEntity.getUploadSpeed());
    }

    @Test
    void mapToEntityOrDto_WithNullSource_ReturnsNull() {
        // Act
        InternetPackage destinationEntity = modelMapper.mapToEntityOrDto(null, InternetPackage.class);

        // Assert
        assertNull(destinationEntity);
    }

    @Test
    void mapToEntityOrDto_WithNullDestinationClass_ReturnsNull() {
        // Arrange
        InternetPackageDTO sourceDto = new InternetPackageDTO();

        // Act
        InternetPackage destinationEntity = modelMapper.mapToEntityOrDto(sourceDto, null);

        // Assert
        assertNull(destinationEntity);
    }
}