package com.simicaleksandar.radar.model;

import com.simicaleksandar.radar.exceptions.ValidationException;

import javax.lang.model.element.Element;

import radar.AdapterFactory;

public class AdapterFactoryAnnotatedInterface extends AnnotatedInterface {

    public AdapterFactoryAnnotatedInterface(Element element) throws
            IllegalArgumentException, ValidationException {
        super(element);
    }

    @Override
    String getKindNotAllowedError() {
        return String.format("Only interfaces can be annotated with @%s",
                AdapterFactory.class.getSimpleName());
    }

    @Override
    public Kind getKind() {
        return Kind.ADAPTER_FACTORY;
    }
}
