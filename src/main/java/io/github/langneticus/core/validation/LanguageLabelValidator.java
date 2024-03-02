package io.github.langneticus.core.validation;

import io.github.langneticus.core.validation.exceptions.ValidationException;
import io.github.langneticus.core.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public final class LanguageLabelValidator extends BaseValidator<String> {
    public static final int MIN_LABEL_LENGTH = 3;
    private final Pattern pattern;
    public LanguageLabelValidator(final BiConsumer<String,Throwable> aThrowableConsumer){
        super(aThrowableConsumer);
        pattern = Pattern.compile("^([A-Z_$][A-Z\\d_$]*)$");
    }
    @Override
    public void validate(@Nullable String arg) throws ValidationException {
        if (arg == null || arg.length() < MIN_LABEL_LENGTH) {
            throw new ValidationException("Detected invalid length key (" + arg + "). Minimum key length is " + MIN_LABEL_LENGTH + ".");
        }
        if (!pattern.matcher(arg.trim()).matches()) {
            throw new ValidationException("Label (" + arg + ") cannot be mapped as a java identifier, regex (^([A-Z_$][A-Z\\d_$]*)$) validation failed.");
        }
    }
}
