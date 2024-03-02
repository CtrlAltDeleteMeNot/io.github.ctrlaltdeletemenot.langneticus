package io.github.langneticus.core.validation;

import io.github.langneticus.core.validation.exceptions.ValidationException;
import io.github.langneticus.core.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class BaseValidator<T> {
    private final BiConsumer<String,Throwable> onWarning;

    protected BaseValidator() {
        onWarning = null;
    }

    protected BaseValidator(@Nullable final BiConsumer<String,Throwable> aOnWarning) {
        onWarning = aOnWarning;
    }

    public boolean isValid(@Nullable T arg) {
        try {
            validate(arg);
            return true;
        } catch (final ValidationException validationException) {
            if (onWarning != null) {
                onWarning.accept("Validation error", validationException);
            }
            return false;
        }
    }

    public boolean isNotValid(@Nullable T arg) {
        return !isValid(arg);
    }

    public abstract void validate(@Nullable T arg) throws ValidationException;

}
