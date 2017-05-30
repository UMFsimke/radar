package com.simicaleksandar.radar;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import radar.ViewHolder;

class ViewHolderAnnotatedClass {

  private int layoutId;
  private String simpleTypeName;
  private String qualifiedClassName;


  public ViewHolderAnnotatedClass(TypeElement classElement) throws IllegalArgumentException {
    ViewHolder annotation = classElement.getAnnotation(ViewHolder.class);
    layoutId = annotation.layoutId();

    if (layoutId == 0) {
      throw new IllegalArgumentException(
          String.format("Layout resource id has to be defined for a view holder @%s",
              classElement.getQualifiedName().toString()));
    }

    qualifiedClassName = classElement.getQualifiedName().toString();
    simpleTypeName = classElement.getSimpleName().toString();
  }

  public int getLayoutId() {
    return layoutId;
  }

  public String getSimpleTypeName() {
    return simpleTypeName;
  }

  public String getQualifiedClassName() {
    return qualifiedClassName;
  }

  public static class Validator {

    public static boolean isValidForParsing(Element element) {

    }
  }
}
