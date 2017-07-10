package com.simicaleksandar.radar.exceptions;

import com.simicaleksandar.radar.model.java.AnnotatedType;

public class QualifiedNameAlreadyUsedException extends Exception {

    public QualifiedNameAlreadyUsedException(AnnotatedType element) {
        super(String.format("Type %s is already annotated.", element.getQualifiedName()));
    }
}
