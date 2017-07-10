package com.simicaleksandar.radar.validation;

import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.model.inner.RecyclerViewAdapterAnnotatedMethod;
import com.simicaleksandar.radar.model.inner.ViewHolderGroupedClasses;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import radar.AdapterFactory;
import radar.RadarAdapter;
import radar.RadarViewHolder;
import radar.RecyclerViewAdapter;

public class RecyclerViewAdapterValidator extends
        MethodValidator<RecyclerViewAdapterAnnotatedMethod> {

    private static RecyclerViewAdapterValidator instance;

    private ViewHolderGroupedClasses viewHolderGroupedClasses;

    public static RecyclerViewAdapterValidator getInstance(Elements elementUtils, Types typeUtils,
                                                           ViewHolderGroupedClasses viewHolderGroupedClasses) {
        if (instance == null) {
            instance = new RecyclerViewAdapterValidator(elementUtils, typeUtils,
                    viewHolderGroupedClasses);
        }

        return instance;
    }

    protected RecyclerViewAdapterValidator(Elements elementUtils, Types typeUtils,
                                           ViewHolderGroupedClasses viewHolderGroupedClasses) {
        super(elementUtils, typeUtils);
        this.viewHolderGroupedClasses = viewHolderGroupedClasses;
    }

    /**
     * Method annotated with {@link RecyclerViewAdapter} must satisfy:
     * <ul>
     *     <li>Must be public</li>
     *     <li>Must be defined in interface annotated with {@link AdapterFactory}</li>
     *     <li>Must not have parameters</li>
     *     <li>Must return {@link RadarAdapter}</li>
     *     <li>Must contain at least view holder class annotated with {@link RadarViewHolder}</li>
     *     <li>Can't contain classes that are not annotated with {@link RadarViewHolder}</li>
     * </ul>
     * @param element
     * @throws ValidationException
     */
    @Override
    public void validate(RecyclerViewAdapterAnnotatedMethod element) throws ValidationException {
        ensureIsAccessibleInGeneratedCode(element, AdapterFactory.class);
        ensureHasNoParameters(element);
        ensureReturnTypeMatches(element, RadarAdapter.class);
        /*adapterFactoryGroupedClasses.ensureExists(element.getEnclosingClassElement());
        viewHolderGroupedClasses.ensureAllExist(element.getAnnotatedViewHolders());*/
    }
}
