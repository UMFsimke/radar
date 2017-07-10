package com.simicaleksandar.radar.validation;

import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.model.java.AnnotatedType;

import java.util.Set;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

public abstract class TypeValidator<T extends AnnotatedType> extends ElementValidator<T> {

    protected void ensureIsAccessibleInGeneratedCode(T annotatedElement, Class<?> annotation)
            throws ValidationException {
        Set<Modifier> modifiers = annotatedElement.getAnnotatedElement().getModifiers();
        if (modifiers.contains(PUBLIC) && !modifiers.contains(ABSTRACT)) return;

        throw new ValidationException("Element %s annotated with @%s must not be private, protected" +
                " or abstract.", annotatedElement.getSimpleTypeName(), annotation.getSimpleName());
    }

    protected boolean ensureHasNoInterfaces(T element) {
        TypeElement currentClass = element.getAnnotatedElement();
        return currentClass.getInterfaces() == null || currentClass.getInterfaces().size() == 0;
    }

    protected void ensureIsNotEnclosed(T element) throws ValidationException {
        if (element.getAnnotatedElement().getEnclosingElement().getKind() == ElementKind.PACKAGE) {
            return;
        }

        throw new ValidationException("Element %s can't be enclosed", element.getQualifiedName());
    }
}
