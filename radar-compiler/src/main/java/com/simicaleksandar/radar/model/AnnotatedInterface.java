package com.simicaleksandar.radar.model;

import com.simicaleksandar.radar.exceptions.ValidationException;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public abstract class AnnotatedInterface extends AnnotatedType {

    public AnnotatedInterface(Element element) throws ValidationException {
        super(element, ElementKind.INTERFACE);
    }
}
