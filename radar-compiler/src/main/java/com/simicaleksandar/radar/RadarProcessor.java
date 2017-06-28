package com.simicaleksandar.radar;

import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;
import com.simicaleksandar.radar.validation.ValidationException;
import com.simicaleksandar.radar.validation.Validator;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import radar.AdapterFactory;
import radar.RecyclerViewAdapter;
import radar.ViewHolder;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PRIVATE;

@AutoService(Processor.class)
public class RadarProcessor extends AbstractProcessor {

  private Types typeUtils;
  private Elements elementUtils;
  private Filer filer;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    typeUtils = processingEnv.getTypeUtils();
    elementUtils = processingEnv.getElementUtils();
    filer = processingEnv.getFiler();
    Logger.init(processingEnv);
  }

  @Override public Set<String> getSupportedAnnotationTypes() {
    Set<String> types = new LinkedHashSet<>();
    for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
      types.add(annotation.getCanonicalName());
    }
    return types;
  }

  private Set<Class<? extends Annotation>> getSupportedAnnotations() {
    Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();

    annotations.add(RecyclerViewAdapter.class);
    annotations.add(ViewHolder.class);
    annotations.add(AdapterFactory.class);

    return annotations;
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.RELEASE_7;
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(ViewHolder.class)) {
      try {
        parseViewHolderElement(annotatedElement);
      } catch (ValidationException e) {
        Logger.error(annotatedElement, e.getLocalizedMessage());
        return true;
      }
    }

    return false;
  }

  private ViewHolderAnnotatedClass parseViewHolderElement(Element annotatedElement) throws
          ValidationException {
    ViewHolderAnnotatedClass viewHolderAnnotatedClass = new
            ViewHolderAnnotatedClass(annotatedElement);
    Validator.newInstance(elementUtils, typeUtils).validate(viewHolderAnnotatedClass);
    return viewHolderAnnotatedClass;
  }
}
