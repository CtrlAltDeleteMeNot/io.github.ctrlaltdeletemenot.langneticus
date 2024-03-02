package io.github.langneticus.core.validation;

import io.github.langneticus.core.validation.exceptions.ValidationException;

import java.util.regex.Pattern;

public class PackageNameValidator extends BaseValidator<String>{
    private final Pattern pattern;
    public PackageNameValidator(){
        super(null);
        pattern = Pattern.compile("([a-z0-9_]+\\.[a-z0-9_]+)+$");
    }
    @Override
    public void validate(String arg) throws ValidationException {
        if(arg == null || arg.isEmpty()){
            throw new ValidationException("Supplied package name is null or empty.");
        }
        if(!pattern.matcher(arg).matches()){
            throw new ValidationException("Supplied package name is not valid.");
        }
    }
}
