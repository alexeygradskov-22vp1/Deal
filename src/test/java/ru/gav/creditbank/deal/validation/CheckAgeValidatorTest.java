package ru.gav.creditbank.deal.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gav.creditbank.deal.validation.validators.CheckAgeValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CheckAgeValidatorTest {

    CheckAgeValidator checkAgeValidator = new CheckAgeValidator();
    ConstraintValidatorContext constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);

    @Test
    public void testValid() {
        var age = LocalDate.now().minusYears(19);
        assertTrue(checkAgeValidator.isValid(age, constraintValidatorContext));
    }

    @Test
    public void testNotValid() {
        var age = LocalDate.now().minusYears(17);
        assertFalse(checkAgeValidator.isValid(age, constraintValidatorContext));
    }
}
