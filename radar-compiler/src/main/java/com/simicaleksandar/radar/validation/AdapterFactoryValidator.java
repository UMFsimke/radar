package com.simicaleksandar.radar.validation;

import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.model.inner.AdapterFactoryAnnotatedInterface;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import radar.AdapterFactory;
import radar.RecyclerViewAdapter;

public class AdapterFactoryValidator extends InterfaceValidator<AdapterFactoryAnnotatedInterface> {

    private static AdapterFactoryValidator instance;

    public static AdapterFactoryValidator getInstance() {
        if (instance == null) {
            instance = new AdapterFactoryValidator();
        }

        return instance;
    }

    protected AdapterFactoryValidator() {
        super();
    }

    /**
     * Interface annotated with {@link AdapterFactory} must satisfy:
     * <ul>
     *     <li>Must be public</li>
     *     <li>Must be interface</li>
     *     <li>Must not extend any other interface</li>
     *     <li>Can't be enclosed in another class, interface or any other structure</li>
     *     <li>Can't have methods that are not annotated with {@link RecyclerViewAdapter}</li>
     * </ul>
     * @param element
     * @throws ValidationException
     */
    @Override
    public void validate(AdapterFactoryAnnotatedInterface element) throws ValidationException {
        ensureIsAccessibleInGeneratedCode(element, AdapterFactory.class);
        ensureHasNoParents(element, AdapterFactory.class);
        ensureIsNotEnclosed(element);
        ensureAllMethodsAreAnnotated(element);
    }

    private void ensureAllMethodsAreAnnotated(AdapterFactoryAnnotatedInterface element) throws
            ValidationException {
        List<? extends Element> enclosedElements = element.getAnnotatedElement()
                .getEnclosedElements();
        if (enclosedElements == null || enclosedElements.size() == 0) {
            throw new ValidationException("Interface %s has no methods annotated with %s, " +
                    "keep your code clean :)", element.getQualifiedName(),
                    RecyclerViewAdapter.class.getSimpleName());
        }

        for (Element enclosedElement : enclosedElements) {
            if (enclosedElement.getKind() != ElementKind.METHOD ||
                    enclosedElement.getAnnotation(RecyclerViewAdapter.class) == null) {
                throw new ValidationException("Interface %s, annotated with %s, can't have anything " +
                        "besides methods that are not annotated with %s.",
                        element.getQualifiedName(),
                        AdapterFactory.class.getSimpleName(),
                        RecyclerViewAdapter.class.getSimpleName());
            }
        }
    }
}
