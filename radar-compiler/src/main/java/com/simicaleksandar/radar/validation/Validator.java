package com.simicaleksandar.radar.validation;


import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.model.inner.AdapterFactoryAnnotatedInterface;
import com.simicaleksandar.radar.model.AnnotatedElement;
import com.simicaleksandar.radar.model.inner.RecyclerViewAdapterAnnotatedMethod;
import com.simicaleksandar.radar.model.inner.ViewHolderAnnotatedClass;

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
            case ADAPTER_FACTORY:
                AdapterFactoryAnnotatedInterface annotatedInterface = annotatedElement.getElement();
                validateAdapterFactory(annotatedInterface);
                return;
            case ADAPTER_PROVIDER:
                RecyclerViewAdapterAnnotatedMethod annotatedMethod = annotatedElement.getElement();
                validateRecyclerViewAdapterMethod(annotatedMethod);
                return;
            default:
                throw new IllegalArgumentException("Validation for given annotation is not implemented");
        }
    }

    private void validateViewHolder(ViewHolderAnnotatedClass annotatedClass) throws
            ValidationException {
        ViewHolderValidator.getInstance(elementUtils, typeUtils).validate(annotatedClass);
    }

    private void validateRecyclerViewAdapterMethod(
            RecyclerViewAdapterAnnotatedMethod annotatedMethod) {
        /*RecyclerViewAdapterValidator.getInstance(elementUtils, typeUtils, )
                .validate(annotatedMethod);*/
    }

    private void validateAdapterFactory(AdapterFactoryAnnotatedInterface annotatedInterface) throws
            ValidationException {
        AdapterFactoryValidator.getInstance().validate(annotatedInterface);
    }
}
