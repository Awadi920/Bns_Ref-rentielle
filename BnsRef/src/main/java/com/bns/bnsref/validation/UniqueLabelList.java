package com.bns.bnsref.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueLabelListValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueLabelList {
    String message() default "Une table Référentielle avec ce nom existe déjà.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
