package com.simicaleksandar.radar.validation;

import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.model.AnnotatedElement;

public abstract class ElementValidator<T extends AnnotatedElement> {

    public abstract void validate(T element) throws ValidationException;

}
