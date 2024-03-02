package io.github.langneticus.core.validation.exceptions;

import io.github.langneticus.core.NotNull;

public class ValidationException extends Exception {

    public ValidationException(@NotNull final String s) {
        super(s);
    }

    public ValidationException(@NotNull final String s, @NotNull final Throwable e) {
        super(s, e);
    }
}
