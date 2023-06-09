package com.gigacapstone.billingservice.model;

import com.gigacapstone.billingservice.enums.TimeUnit;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
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
    @NotEmpty(message = "time unit cannot be empty")
    private TimeUnit timeUnit;

    @Positive
    private int duration;
}
