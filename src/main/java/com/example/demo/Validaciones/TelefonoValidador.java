package com.example.demo.Validaciones;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefonoValidador implements ConstraintValidator<Telefono, Integer> {

    @Override
    public boolean isValid(Integer telefono, ConstraintValidatorContext context) {
        if (telefono == null) return false;

        return telefono >= 100000000 && telefono <= 999999999L;
    }
}

