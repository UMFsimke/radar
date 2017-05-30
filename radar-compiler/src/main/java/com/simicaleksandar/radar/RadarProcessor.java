package com.simicaleksandar.radar;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.STATIC;

import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
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
import radar.RecyclerViewAdapter;
import radar.ViewHolder;

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

    return annotations;
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.RELEASE_7;
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    return false;
  }

  private Map<TypeElement, BindingSet> findAndParseTargets(RoundEnvironment env) {

    for (Element element : env.getElementsAnnotatedWith(RecyclerViewAdapter.class)) {
      if (!SuperficialValidation.validateElement(element)) continue;

      try {
        parseRecyclerViewAdapter(element, builderMap, erasedTargetNames);
      } catch (Exception e) {
        logParsingError(element, BindArray.class, e);
      }
    }
  }

  private void parseViewHolder(Element annotatedElement) {
    boolean hasError = false;

    if (annotatedElement.getKind() != ElementKind.CLASS) {
      Logger.error(annotatedElement, "Only classes can be annotated with @%s",
          ViewHolder.class.getSimpleName());
      hasError = true;
    }


  }



  private void parseRecyclerViewAdapter(Element annotatedElement,
      Map<TypeElement, BindingSet.Builder> builderMap, Set<TypeElement> erasedTargetNames) {

    boolean hasError = false;
    if (annotatedElement.getKind() != ElementKind.CLASS) {
      Logger.error(annotatedElement, "Only classes can be annotated with @%s",
          RecyclerViewAdapter.class.getSimpleName());
      hasError = true; // Exit processing
    }
  }

  private boolean isViewHolderInaccessible(Class<? extends Annotation> annotationClass,
      Element element) {
    Set<Modifier> modifiers = element.getModifiers();
    if (modifiers.contains(PRIVATE) || modifiers.contains(ABSTRACT)) {
      Logger.error(element, "Class %s annotated with @%s must not be private or abstract.",
          element.getSimpleName(), annotationClass.getSimpleName());
      return true;
    }

    return false;
  }
}
