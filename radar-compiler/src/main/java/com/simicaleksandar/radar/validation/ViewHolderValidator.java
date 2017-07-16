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
   *     <li>Can't be enclosed</li>
   *     <li>Can't implement any other interfaces</li>
   * </ul>
   * @param element
   * @throws ValidationException
   */
  //TODO: should we allow enclosing of view holder? if so, it should be static as we don't want to allow variables of outside class to be accessed
  @Override
  public void validate(ViewHolderAnnotatedClass element) throws ValidationException {
    ensureIsAccessibleInGeneratedCode(element, ViewHolder.class);
    ensureIsNotEnclosed(element);
    ensureIsImplementingInterface(element, RadarViewHolder.class, ViewHolder.class);
    ensureHasEmptyConstructor(element);
    //TODO: add not implementing any other interface
  }
}
