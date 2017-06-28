package com.simicaleksandar.radar;

import com.simicaleksandar.radar.validation.ValidationException;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;

import radar.ViewHolder;

public abstract class AnnotatedClass extends AnnotatedElement {

    protected String simpleTypeName;
    protected String qualifiedClassName;
    protected TypeElement annotatedClassElement;

    public AnnotatedClass(Element element) throws IllegalArgumentException, ValidationException {
        if (element.getKind() != ElementKind.CLASS) {
            throw new ValidationException(String.format("Only classes can be annotated with @%s",
                    ViewHolder.class.getSimpleName()));
        }

        annotatedClassElement = (TypeElement) element;
        readRequiredInfo();
    }

    protected void readRequiredInfo() {
       qualifiedClassName = annotatedClassElement.getQualifiedName().toString();
       simpleTypeName = annotatedClassElement.getSimpleName().toString();
    }

    /**
     * Gets simple name of type annotated with {@link ViewHolder}
     * @return Simple class name
     */
    public String getSimpleTypeName() {
        return simpleTypeName;
    }

    /**
     * Gets the full qualified name of type annotated with {@link ViewHolder}
     *
     * @return full qualified name
     */
    public String getQualifiedClassName() {
        return qualifiedClassName;
    }

    /**
     * Gets original element annotated with {@link ViewHolder}
     *
     * @return Original element
     */
    public TypeElement getAnnotatedClassElement() {
        return annotatedClassElement;
    }
}
