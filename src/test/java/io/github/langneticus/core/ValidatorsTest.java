package io.github.langneticus.core;


import io.github.langneticus.core.validation.LanguageTagValidator;
import io.github.langneticus.core.validation.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorsTest {
    @Test
    public void validateLanguageTag() throws ValidationException {
        final String valid = "en-US";
        final LanguageTagValidator validator = new LanguageTagValidator();
        validator.validate(valid);
    }

    @Test
    public void validateLanguageTagWithNoCountryInfoThrows() throws ValidationException {
        final ValidationException exception = assertThrows(ValidationException.class, () -> {
            final String nonValid = "fr";
            final LanguageTagValidator validator = new LanguageTagValidator();
            validator.validate(nonValid);
        });
        assertEquals("Language tag fr does not contain country information.", exception.getMessage());
    }

    @Test
    public void validateLanguageTagWithInvalidCountryThrows() throws ValidationException {
        final ValidationException exception = assertThrows(ValidationException.class, () -> {
            final String nonValid = "fr-ZZZ";
            final LanguageTagValidator validator = new LanguageTagValidator();
            validator.validate(nonValid);
        });
        assertEquals("Language tag fr-ZZZ does not contain country information.", exception.getMessage());
    }

    @Test
    public void validateLanguageTagWithInvalidInfoThrows() throws ValidationException {
        final ValidationException exception = assertThrows(ValidationException.class, () -> {
            final String nonValid = "zzZZZ";
            final LanguageTagValidator validator = new LanguageTagValidator();
            validator.validate(nonValid);
        });
        assertEquals("Language tag zzZZZ is not valid.", exception.getMessage());
    }
}
