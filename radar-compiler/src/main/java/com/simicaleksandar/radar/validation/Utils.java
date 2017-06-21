package com.simicaleksandar.radar.validation;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

class Utils {

  static boolean isAnnotatedElementClass(Element element) {
    return isAnnotatedElementOfKind(element, ElementKind.CLASS);
  }

  private static boolean isAnnotatedElementOfKind(Element element, ElementKind elementKind) {
    return element.getKind() != elementKind;
  }
}
