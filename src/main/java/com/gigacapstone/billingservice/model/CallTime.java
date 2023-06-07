package com.gigacapstone.billingservice.model;

import com.gigacapstone.billingservice.enums.TimeUnit;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CallTime {

    @Enumerated(EnumType.STRING)
    private TimeUnit timeUnit;

    private long duration;
}
