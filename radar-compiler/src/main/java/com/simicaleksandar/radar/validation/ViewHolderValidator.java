package com.simicaleksandar.radar.validation;

import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import radar.ViewHolder;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

final class ViewHolderValidator {

  static void validate(Element annotatedElement) throws ValidationException {
    ensureIsClassAnnotated(annotatedElement);
    ensureIsAccessibleInGeneratedCode(annotatedElement);
  }

  private static void ensureIsClassAnnotated(Element annotatedElement) throws ValidationException {
    if (Utils.isAnnotatedElementClass(annotatedElement)) return;

    throw new ValidationException("Only classes can be annotated with @%s",
          ViewHolder.class.getSimpleName());
  }

  private static void ensureIsAccessibleInGeneratedCode(Element annotatedElement)
          throws ValidationException {
    Set<Modifier> modifiers = annotatedElement.getModifiers();
    if (modifiers.contains(PUBLIC) && !modifiers.contains(ABSTRACT)) return;

  throw new ValidationException("Class %s annotated with @%s must not be private, protected" +
          " or abstract.",
          annotatedElement.getSimpleName(),
          ViewHolder.class.getSimpleName());
  }

}
