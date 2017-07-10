package com.simicaleksandar.radar.validation;

import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.model.java.AnnotatedClass;

import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

public abstract class ClassValidator<T extends AnnotatedClass> extends TypeValidator<T> {

    private Elements elementUtils;
    private Types typeUtils;

    protected ClassValidator(Elements elementUtils, Types typeUtils) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
    }

    protected void ensureIsImplementingInterface(T element, Class<?> interfaceClass,
                                                 Class<?> annotation)
            throws ValidationException {
        TypeElement currentClass = element.getAnnotatedElement();
        TypeMirror interfaceTypeMirror = elementUtils.getTypeElement(interfaceClass.getName())
                .asType();
        TypeMirror strippedGenericInterface = typeUtils.erasure(interfaceTypeMirror);
        while (true) {
            TypeMirror superClassType = currentClass.getSuperclass();
            for (TypeMirror implementedInterface : currentClass.getInterfaces()) {
                TypeMirror strippedGenericImplementedInterface =
                        typeUtils.erasure(implementedInterface);
                if (typeUtils.isSameType(
                        strippedGenericImplementedInterface, strippedGenericInterface)) {
                    return;
                }
            }

            if (superClassType.getKind() == TypeKind.NONE) {
                break;
            }

            currentClass = (TypeElement) typeUtils.asElement(superClassType);
        }

        throw new ValidationException("The class %s annotated with @%s must implement the interface %s",
                element.getSimpleTypeName(), annotation.getSimpleName(),
                interfaceClass.getCanonicalName());
    }

    protected void ensureHasEmptyConstructor(T element) throws ValidationException {
        for (Element enclosed : element.getAnnotatedElement().getEnclosedElements()) {
            if (enclosed.getKind() != ElementKind.CONSTRUCTOR) continue;

            ExecutableElement constructorElement = (ExecutableElement) enclosed;
            if (constructorElement.getParameters().size() == 0 && constructorElement.getModifiers()
                    .contains(Modifier.PUBLIC)) {
                return;
            }
        }

        throw new ValidationException("The class %s must provide an public empty default constructor",
                element.getQualifiedName());
    }
}
