package io.github.langneticus.core.validation;

import io.github.langneticus.core.validation.exceptions.ValidationException;
import io.github.langneticus.core.Nullable;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class LanguageTagValidator extends BaseValidator<String> {
    public LanguageTagValidator() {
        super(null);
    }
    public LanguageTagValidator(final BiConsumer<String, Throwable> aThrowableConsumer) {
        super(aThrowableConsumer);
    }

    @Override
    public void validate(@Nullable final String arg) throws ValidationException {
        if (arg == null || arg.isEmpty()) {
            throw new ValidationException("Null or empty language tag.");
        }
        try {
            final Locale.Builder localeBuilder = new Locale.Builder();
            localeBuilder.setLanguageTag(arg);
            final Locale locale = localeBuilder.build();
            if (locale.getISO3Language() == null || locale.getISO3Language().isEmpty()) {
                throw new ValidationException("Language tag " + arg + " does not contain language information.");
            }
            if (locale.getISO3Country() == null || locale.getISO3Country().isEmpty()) {
                throw new ValidationException("Language tag " + arg + " does not contain country information.");
            }
        } catch (final MissingResourceException e) {
            throw new ValidationException("Language tag " + arg + " is not valid.", e);
        }
    }
}
