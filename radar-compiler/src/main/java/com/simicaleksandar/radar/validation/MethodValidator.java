package com.simicaleksandar.radar.validation;

import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.model.AnnotatedMethod;

import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

public abstract class MethodValidator<T extends AnnotatedMethod> extends ElementValidator<T> {

    private Elements elementUtils;
    private Types typeUtils;

    protected MethodValidator(Elements elementUtils, Types typeUtils) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
    }

    protected void ensureIsAccessibleInGeneratedCode(T annotatedElement, Class<?> annotation)
            throws ValidationException {
        Set<Modifier> modifiers = annotatedElement.getAnnotatedMethodElement().getModifiers();
        if (modifiers.contains(PUBLIC) && !modifiers.contains(ABSTRACT)
                && !modifiers.contains(STATIC)) return;

        throw new ValidationException("Method %s, at %s, annotated with @%s " +
                "must not be private, protected or abstract.",
                annotatedElement.getMethodName(), annotation.getSimpleName());
    }

    protected void ensureReturnTypeMatches(T annotatedElement, Class<?> returnClass) throws
            ValidationException {
        TypeMirror expectedReturnTypeMirror = elementUtils.getTypeElement(returnClass.getName())
                .asType();
        expectedReturnTypeMirror = typeUtils.erasure(expectedReturnTypeMirror);

        TypeMirror returnTypeMirror = annotatedElement.getAnnotatedMethodElement().getReturnType();
        returnTypeMirror = typeUtils.erasure(returnTypeMirror);

        if (typeUtils.isSameType(returnTypeMirror, expectedReturnTypeMirror)) return;
        //TODO: what if return type can be extended and child is returned? It should be added to validation

        throw new ValidationException("Method %s, at %s, must return instance of %s",
                annotatedElement.getMethodName(),
                annotatedElement.getEnclosingClassElement().getQualifiedName(),
                returnClass.getCanonicalName());
    }

    protected void ensureHasNoParameters(T annotatedElement) throws ValidationException {
        if (annotatedElement.getParameters() == null ||
                annotatedElement.getParameters().size() == 0) return;

        throw new ValidationException("Method %s, at %s, can't have parameters",
                annotatedElement.getMethodName(),
                annotatedElement.getEnclosingClassElement().getQualifiedName());
    }
}
