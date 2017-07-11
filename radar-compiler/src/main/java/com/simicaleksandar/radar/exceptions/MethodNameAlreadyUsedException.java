package com.simicaleksandar.radar.exceptions;

import com.simicaleksandar.radar.model.java.AnnotatedMethod;

public class MethodNameAlreadyUsedException extends Exception {

    public MethodNameAlreadyUsedException(AnnotatedMethod element) {
        super(String.format("Method with %s name is already annotated.", element.getMethodName()));
    }
}
