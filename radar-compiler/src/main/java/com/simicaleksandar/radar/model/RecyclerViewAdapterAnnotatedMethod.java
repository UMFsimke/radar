package com.simicaleksandar.radar.model;

import com.simicaleksandar.radar.exceptions.ValidationException;

import javax.lang.model.element.Element;

import radar.RecyclerViewAdapter;

public class RecyclerViewAdapterAnnotatedMethod extends AnnotatedMethod {

    protected Class<?>[] annotatedViewHolders;

    public RecyclerViewAdapterAnnotatedMethod(Element element) throws
            IllegalArgumentException, ValidationException {
        super(element);
        validate();
    }

    @Override
    protected void readRequiredInfo() {
        super.readRequiredInfo();
        RecyclerViewAdapter annotation = annotatedMethodElement
                .getAnnotation(RecyclerViewAdapter.class);
        Class<?>[] viewHolderClasses = annotation.viewHolders();
        readViewHoldersTypeElements(viewHolderClasses);
    }

    private void readViewHoldersTypeElements(Class<?>[] viewHolderClasses) {
        if (viewHolderClasses == null) return;

        annotatedViewHolders = viewHolderClasses;
    }

    private void validate() throws IllegalArgumentException {
        if (annotatedViewHolders != null && annotatedViewHolders.length > 0) return;

        throw new IllegalArgumentException(
                    String.format("Adapter constructed with method %s, at %s, doesnt has any view holders",
                            methodName, enclosingClassElement.getQualifiedName().toString()));
    }

    public Class<?>[] getAnnotatedViewHolders() {
        return annotatedViewHolders;
    }

    @Override
    public Kind getKind() {
        return Kind.ADAPTER_PROVIDER;
    }
}
