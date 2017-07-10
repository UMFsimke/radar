package com.simicaleksandar.radar.validation;

import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.model.inner.RecyclerViewAdapterAnnotatedMethod;

import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import radar.AdapterFactory;
import radar.RadarAdapter;
import radar.RadarViewHolder;
import radar.RecyclerViewAdapter;
import radar.ViewHolder;

public class RecyclerViewAdapterValidator extends
        MethodValidator<RecyclerViewAdapterAnnotatedMethod> {

    private static RecyclerViewAdapterValidator instance;

    public static RecyclerViewAdapterValidator getInstance(Elements elementUtils, Types typeUtils) {
        if (instance == null) {
            instance = new RecyclerViewAdapterValidator(elementUtils, typeUtils);
        }

        return instance;
    }

    protected RecyclerViewAdapterValidator(Elements elementUtils, Types typeUtils) {
        super(elementUtils, typeUtils);
    }

    /**
     * Method annotated with {@link RecyclerViewAdapter} must satisfy:
     * <ul>
     *     <li>Must be defined in interface annotated with {@link AdapterFactory}</li>
     *     <li>Must not have parameters</li>
     *     <li>Must return {@link RadarAdapter}</li>
     *     <li>Must contain at least one view holder class annotated with {@link RadarViewHolder}</li>
     *     <li>Can't contain classes that are not annotated with {@link RadarViewHolder}</li>
     *     <li>Can't contain classes duplicated view holder classes</li>
     * </ul>
     * @param element
     * @throws ValidationException
     */
    @Override
    public void validate(RecyclerViewAdapterAnnotatedMethod element) throws ValidationException {
        ensureHasNoParameters(element);
        ensureReturnTypeMatches(element, RadarAdapter.class);
        ensureIsEnclosedInAFactory(element);
        ensureViewHoldersAreValidElements(element);
        ensureThereAreNoDuplicatedViewHolders(element);
    }

    private void ensureIsEnclosedInAFactory(RecyclerViewAdapterAnnotatedMethod element) throws
            ValidationException {
        Element enclosingElement = element.getAnnotatedMethodElement().getEnclosingElement();
        if (enclosingElement.getAnnotation(AdapterFactory.class) != null) return;

        throw new ValidationException("Method %s, at %s, must be enclosed in " +
                "interface annotated with %s", element.getMethodName(),
                element.getEnclosingClassElement().getQualifiedName(),
                AdapterFactory.class.getSimpleName());
    }

    private void ensureViewHoldersAreValidElements(RecyclerViewAdapterAnnotatedMethod element)
            throws ValidationException {
        List<Element> viewHolderElements = element.getAnnotatedViewHolders();
        for (Element viewHolderElement : viewHolderElements) {
            if (viewHolderElement.getAnnotation(ViewHolder.class) != null) continue;

            throw new ValidationException("Method %s, at %s, as view holders can contain only " +
                    "classes annotated with %s", element.getMethodName(),
                    element.getEnclosingClassElement().getQualifiedName(),
                    ViewHolder.class.getSimpleName());
        }
    }

    private void ensureThereAreNoDuplicatedViewHolders(RecyclerViewAdapterAnnotatedMethod element)
            throws ValidationException {
        List<Element> viewHolderElements = element.getAnnotatedViewHolders();
        HashMap<String, Element> viewHolderElementsMap = new HashMap<>();
        for (Element viewHolderElement : viewHolderElements) {
            String qualifiedName = ((TypeElement) viewHolderElement)
                    .getQualifiedName().toString();
            if (viewHolderElementsMap.containsKey(qualifiedName)) {
                throw new ValidationException("Method %s, at %s, has one or more " +
                        "duplicated view holders", element.getMethodName(),
                        element.getEnclosingClassElement().getQualifiedName());
            }

            viewHolderElementsMap.put(qualifiedName, viewHolderElement);
        }
    }
}
