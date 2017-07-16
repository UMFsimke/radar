package com.simicaleksandar.radar.model.inner;

import android.support.annotation.LayoutRes;

import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.model.java.AnnotatedClass;
import com.squareup.javapoet.CodeBlock;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import radar.RadarViewHolder;
import radar.ViewHolder;

public class ViewHolderAnnotatedClass extends AnnotatedClass {

  private LayoutId layoutId;
  private Types typeUtils;
  private Elements elementUtils;
  private TypeMirror displayedModel;

  public ViewHolderAnnotatedClass(Element element, Types typeUtils, Elements elementUtils) throws
          IllegalArgumentException, ValidationException {
    super(element);
    this.typeUtils = typeUtils;
    this.elementUtils = elementUtils;
    readDisplayedModel();
    validate();
  }

  @Override
  protected String getKindNotAllowedError() {
    return String.format("Only classes can be annotated with @%s",
            ViewHolder.class.getSimpleName());
  }

  /**
   * Reads required data from class element for a class that is annotated with
   * {@link ViewHolder} annotation.
   */
  @Override
  protected void readRequiredInfo() {
    super.readRequiredInfo();
    ViewHolder annotation = annotatedElement.getAnnotation(ViewHolder.class);
    layoutId = new LayoutId(annotation.layoutId());
  }

  private void readDisplayedModel() {
    TypeElement currentClass = annotatedElement;
    TypeMirror interfaceTypeMirror = elementUtils.getTypeElement(RadarViewHolder.class.getName())
            .asType();
    TypeMirror strippedGenericInterface = typeUtils.erasure(interfaceTypeMirror);
    while (true) {
      TypeMirror superClassType = currentClass.getSuperclass();
      for (TypeMirror implementedInterface : currentClass.getInterfaces()) {
        TypeMirror strippedGenericImplementedInterface =
                typeUtils.erasure(implementedInterface);
        if (typeUtils.isSameType(
                strippedGenericImplementedInterface, strippedGenericInterface)) {
          displayedModel = ((DeclaredType) implementedInterface).getTypeArguments().get(0);
          return;
        }
      }

      if (superClassType.getKind() == TypeKind.NONE) {
        break;
      }

      currentClass = (TypeElement) typeUtils.asElement(superClassType);
    }
  }
  /**
   * Validates if developer provided all required annotation fields. If layout is not provided
   * then {@link IllegalArgumentException} is thrown
   *
   * @throws IllegalArgumentException Exc
   */
  private void validate() throws IllegalArgumentException {
    if (layoutId.isValid()) return;

    throw new IllegalArgumentException(
            String.format("Layout resource id has to be defined for a view holder at @%s",
                    qualifiedName));
  }

  /**
   * Gets layout id that is specified in {@link ViewHolder#layoutId()}
   *
   * @return {@link LayoutId}
   */
  public LayoutId getLayoutId() {
    return layoutId;
  }

  public TypeMirror getDisplayedModel() {
    return this.displayedModel;
  }

  @Override
  public Kind getKind() {
    return Kind.VIEW_HOLDER;
  }

  public static class LayoutId {

    private final @LayoutRes int layoutId;
    private final CodeBlock codeBlock;

    private LayoutId(@LayoutRes int layoutId) {
      this.layoutId = layoutId;
      codeBlock = CodeBlock.of("$L", layoutId);
    }

    private boolean isValid() {
      //TODO: check if layout ID (resource ID in general) can be negative, if it can't change to >
      return layoutId != 0;
    }

    public CodeBlock asCode() {
      return codeBlock;
    }
  }
}
