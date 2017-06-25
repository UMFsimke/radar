package com.simicaleksandar.radar.validation;

import com.simicaleksandar.radar.AnnotatedElement;

import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

import radar.ViewHolder;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

public abstract class ElementValidator<T extends AnnotatedElement> {

    public abstract void validate(T element) throws ValidationException;





}
