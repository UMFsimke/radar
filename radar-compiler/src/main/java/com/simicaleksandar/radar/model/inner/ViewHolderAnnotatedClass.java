package com.simicaleksandar.radar.model.inner;

import android.support.annotation.LayoutRes;

import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.model.java.AnnotatedClass;

import javax.lang.model.element.Element;

import radar.ViewHolder;

public class ViewHolderAnnotatedClass extends AnnotatedClass {

  private @LayoutRes int layoutId;

  public ViewHolderAnnotatedClass(Element element) throws
          IllegalArgumentException, ValidationException {
    super(element);
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
    layoutId = annotation.layoutId();
  }

  /**
   * Validates if developer provided all required annotation fields. If layout is not provided
   * then {@link IllegalArgumentException} is thrown
   *
   * @throws IllegalArgumentException Exc
   */
  private void validate() throws IllegalArgumentException {
    //TODO: check if layout ID (resource ID in general) can be negative, if it can't change to >
    if (layoutId != 0) return;

    throw new IllegalArgumentException(
            String.format("Layout resource id has to be defined for a view holder at @%s",
                    qualifiedName));
  }

  /**
   * Gets layout id that is specified in {@link ViewHolder#layoutId()}
   *
   * @return Layout ID
   */
  public @LayoutRes int getLayoutId() {
    return layoutId;
  }

  @Override
  public Kind getKind() {
    return Kind.VIEW_HOLDER;
  }
}
