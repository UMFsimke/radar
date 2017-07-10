package com.simicaleksandar.radar.model.java;

import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.model.AnnotatedElement;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public abstract class AnnotatedType extends AnnotatedElement {

    protected String simpleTypeName;
    protected String qualifiedName;
    protected TypeElement annotatedElement;

    public AnnotatedType(Element element, ElementKind kind) throws ValidationException {
        if (element.getKind() != kind) {
            throw new ValidationException(getKindNotAllowedError());
        }

        annotatedElement = (TypeElement) element;
        readRequiredInfo();
    }

    protected abstract String getKindNotAllowedError();

    protected void readRequiredInfo() {
        qualifiedName = annotatedElement.getQualifiedName().toString();
        simpleTypeName = annotatedElement.getSimpleName().toString();
    }

    /**
     * Gets simple name of annotated type
     * @return Simple class name
     */
    public String getSimpleTypeName() {
        return simpleTypeName;
    }

    /**
     * Gets the full qualified name of annotated type
     *
     * @return full qualified name
     */
    public String getQualifiedName() {
        return qualifiedName;
    }

    /**
     * Gets original annotated element
     *
     * @return Original element
     */
    public TypeElement getAnnotatedElement() {
        return annotatedElement;
    }
}
