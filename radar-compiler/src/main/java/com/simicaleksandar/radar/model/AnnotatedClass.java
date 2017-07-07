package com.simicaleksandar.radar.model;

import com.simicaleksandar.radar.exceptions.ValidationException;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public abstract class AnnotatedClass extends AnnotatedType {

    public AnnotatedClass(Element element) throws ValidationException {
        super(element, ElementKind.CLASS);
    }
}
