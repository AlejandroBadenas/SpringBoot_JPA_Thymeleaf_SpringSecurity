package com.example.demo.Validaciones;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TelefonoValidador.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Telefono {
    String message() default "El teléfono debe tener exactamente 10 dígitos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
