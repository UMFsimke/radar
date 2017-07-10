package com.simicaleksandar.radar.model.java;

import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.model.AnnotatedElement;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import radar.RecyclerViewAdapter;

public abstract class AnnotatedMethod extends AnnotatedElement {

    protected String methodName;
    protected List<? extends VariableElement> parameters;
    protected ExecutableElement annotatedMethodElement;
    protected TypeElement enclosingClassElement;

    public AnnotatedMethod(Element element) throws IllegalArgumentException, ValidationException {
        if (!(element instanceof ExecutableElement) || element.getKind() != ElementKind.METHOD) {
            throw new ValidationException(String.format("Only classes can be annotated with @%s",
                    RecyclerViewAdapter.class.getSimpleName()));
        }

        annotatedMethodElement = (ExecutableElement) element;
        readRequiredInfo();
    }

    protected void readRequiredInfo() {
        enclosingClassElement = (TypeElement) annotatedMethodElement.getEnclosingElement();
        methodName = annotatedMethodElement.getSimpleName().toString();
        parameters = annotatedMethodElement.getParameters();
    }

    public String getMethodName() {
        return methodName;
    }

    public List<? extends VariableElement> getParameters() {
        return parameters;
    }

    public ExecutableElement getAnnotatedMethodElement() {
        return annotatedMethodElement;
    }

    public TypeElement getEnclosingClassElement() {
        return enclosingClassElement;
    }
}