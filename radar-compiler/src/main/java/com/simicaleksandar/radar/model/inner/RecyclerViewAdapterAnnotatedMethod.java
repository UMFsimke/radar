package com.simicaleksandar.radar.model.inner;

import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.model.java.AnnotatedMethod;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import radar.RecyclerViewAdapter;

public class RecyclerViewAdapterAnnotatedMethod extends AnnotatedMethod {

    protected List<Element> annotatedViewHolders;

    public RecyclerViewAdapterAnnotatedMethod(Element element, Elements elementUtils) throws
            IllegalArgumentException, ValidationException {
        super(element, elementUtils);
        validate();
    }

    @Override
    protected void readRequiredInfo() {
        super.readRequiredInfo();
        RecyclerViewAdapter annotation = annotatedMethodElement
                .getAnnotation(RecyclerViewAdapter.class);
        try {
            Class<?>[] viewHolderClasses = annotation.viewHolders();
            readViewHoldersTypeElements(viewHolderClasses);
        } catch (MirroredTypesException mte) {
            List<? extends TypeMirror> typeMirrors = mte.getTypeMirrors();
            readViewHoldersElements(typeMirrors);
        }
    }

    private void readViewHoldersTypeElements(Class<?>[] viewHolderClasses) {
        if (viewHolderClasses == null) return;

        annotatedViewHolders = new ArrayList<>(viewHolderClasses.length);
        for (Class viewHolderClass : viewHolderClasses) {
            Element element = elementUtils.getTypeElement(viewHolderClass.getName());
            annotatedViewHolders.add(element);
        }
    }

    private void readViewHoldersElements(List<? extends TypeMirror> typeMirrors) {
        if (typeMirrors == null) return;

        annotatedViewHolders = new ArrayList<>(typeMirrors.size());
        for (TypeMirror typeMirror : typeMirrors) {
            annotatedViewHolders.add(((DeclaredType) typeMirror).asElement());
        }
    }

    private void validate() throws IllegalArgumentException {
        if (annotatedViewHolders != null && annotatedViewHolders.size() > 0) return;

        throw new IllegalArgumentException(
                    String.format("Adapter constructed with method %s, at %s, doesnt has any view holders",
                            methodName, enclosingClassElement.getQualifiedName().toString()));
    }

    public List<Element> getAnnotatedViewHolders() {
        return annotatedViewHolders;
    }

    @Override
    public Kind getKind() {
        return Kind.ADAPTER_PROVIDER;
    }
}
