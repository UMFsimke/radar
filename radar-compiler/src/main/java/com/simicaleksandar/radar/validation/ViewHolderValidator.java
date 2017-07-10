package com.simicaleksandar.radar.validation;

import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.model.inner.ViewHolderAnnotatedClass;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import radar.RadarViewHolder;
import radar.ViewHolder;

final class ViewHolderValidator extends ClassValidator<ViewHolderAnnotatedClass> {

  private static ViewHolderValidator instance;

  public static ViewHolderValidator getInstance(Elements elementUtils, Types typeUtils) {
    if (instance == null) {
      instance = new ViewHolderValidator(elementUtils, typeUtils);
    }

    return instance;
  }

  protected ViewHolderValidator(Elements elementUtils, Types typeUtils) {
    super(elementUtils, typeUtils);
  }

  /**
   * Class annotated with {@link ViewHolder} must satisfy:
   * <ul>
   *     <li>Must be public</li>
   *     <li>Can't be abstract</li>
   *     <li>Must implement or be subclass of superclass that implements {@link RadarViewHolder}</li>
   *     <li>Must have empty constructor</li>
   * </ul>
   * @param element
   * @throws ValidationException
   */
  @Override
  public void validate(ViewHolderAnnotatedClass element) throws ValidationException {
    ensureIsAccessibleInGeneratedCode(element, ViewHolder.class);
    ensureIsImplementingInterface(element, RadarViewHolder.class, ViewHolder.class);
    ensureHasEmptyConstructor(element);
  }
}
