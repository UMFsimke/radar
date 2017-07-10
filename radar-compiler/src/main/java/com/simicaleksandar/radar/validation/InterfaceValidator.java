package com.simicaleksandar.radar.validation;

import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.model.java.AnnotatedInterface;

import java.util.Set;

import javax.lang.model.element.Modifier;

import static javax.lang.model.element.Modifier.PUBLIC;

public abstract class InterfaceValidator<T extends AnnotatedInterface> extends TypeValidator<T> {

    @Override
    protected void ensureIsAccessibleInGeneratedCode(T annotatedElement, Class<?> annotation) throws ValidationException {
        Set<Modifier> modifiers = annotatedElement.getAnnotatedElement().getModifiers();
        if (modifiers.contains(PUBLIC)) return;

        throw new ValidationException("Element %s annotated with @%s must not be private or protected",
                annotatedElement.getSimpleTypeName(), annotation.getSimpleName());
    }

    protected void ensureHasNoParents(T element, Class<?> annotation) throws ValidationException {
        boolean hasNoParents = ensureHasNoInterfaces(element);
        if (hasNoParents) return;

        throw new ValidationException("Interface %s, annotated with %s, can't have parent.",
                element.getQualifiedName(), annotation.getSimpleName());
    }
}
