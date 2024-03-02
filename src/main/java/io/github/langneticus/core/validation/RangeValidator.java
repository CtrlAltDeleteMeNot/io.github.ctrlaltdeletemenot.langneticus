package io.github.langneticus.core.validation;

import io.github.langneticus.core.validation.exceptions.ValidationException;
import io.github.langneticus.core.Nullable;

public class RangeValidator extends BaseValidator<Integer>{
    private final int lo;
    private final int high;
    public RangeValidator(final int aLo, final int aHigh){
        super();
        lo  = aLo;
        high = aHigh;
    }
    @Override
    public void validate(@Nullable Integer arg) throws ValidationException {
        if(arg == null){
            throw new ValidationException("Null is out of range.");
        }
        if(arg < lo){
            throw new ValidationException("Argument "+arg+" is out of range.");
        }
        if(arg > high){
            throw new ValidationException("Argument "+arg+" is out of range.");
        }
    }
}
