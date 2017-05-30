package com.simicaleksandar.radar.validation;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PRIVATE;

import com.simicaleksandar.radar.Logger;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import radar.ViewHolder;

class ViewHolderValidator {

  ValidationResult validate(Element annotatedElement) {
    ensureIsClassAnnotated(annotatedElement);
    ensureIsAccessibleInGeneratedCode(annotatedElement);

    if (annotatedElement.getKind() != ElementKind.CLASS) {
      return ValidationResult.error("Only classes can be annotated with @%s",
          ViewHolder.class.getSimpleName());
    }

    Set<Modifier> modifiers = annotatedElement.getModifiers();
    if (modifiers.contains(PRIVATE) || modifiers.contains(ABSTRACT)) {
      return ValidationResult.error("Class %s annotated with @%s must not be private or abstract.",
          annotatedElement.getSimpleName(), ViewHolder.class.getSimpleName());
    }


  }

  private void ensureIsClassAnnotated(Element annotatedElement) throws ValidationException {
    if (Utils.isAnnotatedElementClass(annotatedElement)) return;

    throw new ValidationException("Only classes can be annotated with @%s",
          ViewHolder.class.getSimpleName());
    }
  }
}
