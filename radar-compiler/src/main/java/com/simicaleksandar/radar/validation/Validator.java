package com.simicaleksandar.radar.validation;


import com.simicaleksandar.radar.AnnotatedElement;
import com.simicaleksandar.radar.ViewHolderAnnotatedClass;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class Validator {

    private static Validator instance;
    private Elements elementUtils;
    private Types typeUtils;

    public static Validator newInstance(Elements elementUtils, Types typeUtils) {
        if (instance == null) {
            instance = new Validator(elementUtils, typeUtils);
        }

        return instance;
    }

    private Validator(Elements elementUtils, Types typeUtils) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
    }

    public void validate(AnnotatedElement annotatedElement) throws ValidationException {
        validate(annotatedElement, annotatedElement.getKind());
    }

    private void validate(AnnotatedElement annotatedElement, AnnotatedElement.Kind kind)
            throws ValidationException {
        switch (kind) {
            case VIEW_HOLDER:
                ViewHolderAnnotatedClass viewHolderAnnotatedClass = annotatedElement.getElement();
                validateViewHolder(viewHolderAnnotatedClass);
                return;
        }
    }

    private void validateViewHolder(ViewHolderAnnotatedClass annotatedClass) throws
            ValidationException {
        ViewHolderValidator.getInstance(elementUtils, typeUtils).validate(annotatedClass);
    }
}
