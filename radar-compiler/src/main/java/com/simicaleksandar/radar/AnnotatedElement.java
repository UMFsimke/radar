package com.simicaleksandar.radar;

public abstract class AnnotatedElement {

    public enum Kind {
        VIEW_HOLDER,
        ADAPTER_PROVIDER,
        ADAPTER_FACTORY
    }

    public abstract Kind getKind();

    @SuppressWarnings("unchecked")
    public <T> T getElement() {
        return (T) this;
    }
}
