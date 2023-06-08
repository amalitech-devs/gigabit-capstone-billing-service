package com.gigacapstone.billingservice.dto.mappers;

import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Component
public class ModelMapper {

    public <T, U> U mapToEntityOrDto(T source, Class<U> destinationClass) {
        U destination = null;

        try {

            Constructor<U> destinationConstructor = destinationClass.getDeclaredConstructor();
            destination = destinationConstructor.newInstance();

            Field[] sourceFields = source.getClass().getDeclaredFields();
            Field[] destinationFields = destinationClass.getDeclaredFields();

            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                Object sourceValue = sourceField.get(source);
                for (Field destinationField : destinationFields) {
                    destinationField.setAccessible(true);
                    if (sourceField.getName().equals(destinationField.getName())) {
                        destinationField.set(destination, sourceValue);
                        break;
                    }
                }
            }
        } catch (NoSuchMethodException
                 | IllegalAccessException
                 | InstantiationException
                 | InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return destination;
    }
}

