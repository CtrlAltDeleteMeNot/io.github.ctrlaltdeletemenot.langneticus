package io.github.langneticus.core.validation;

import io.github.langneticus.core.validation.exceptions.ValidationException;

import java.util.regex.Pattern;

public class ClassNameValidator extends BaseValidator<String>{
    private final Pattern pattern;
    public ClassNameValidator(){
        super(null);
        pattern = Pattern.compile("[A-Za-z_$]+[a-zA-Z0-9_$]*");
    }
    @Override
    public void validate(String arg) throws ValidationException {
        if(arg == null || arg.isEmpty()){
            throw new ValidationException("Supplied class name is null or empty.");
        }
        if(!pattern.matcher(arg).matches()){
            throw new ValidationException("Supplied class name is not valid.");
        }
    }
}
