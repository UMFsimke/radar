package com.simicaleksandar.radar;

import com.google.auto.service.AutoService;
import com.simicaleksandar.radar.exceptions.MethodNameAlreadyUsedException;
import com.simicaleksandar.radar.exceptions.QualifiedNameAlreadyUsedException;
import com.simicaleksandar.radar.exceptions.ValidationException;
import com.simicaleksandar.radar.generator.ViewHolderGroupGenerator;
import com.simicaleksandar.radar.model.inner.AdapterFactoryAnnotatedInterface;
import com.simicaleksandar.radar.model.inner.AdapterFactoryGroupedInterfaces;
import com.simicaleksandar.radar.model.inner.RecyclerViewAdapterAnnotatedMethod;
import com.simicaleksandar.radar.model.inner.RecyclerViewAdapterGroupedMethods;
import com.simicaleksandar.radar.model.inner.ViewHolderAnnotatedClass;
import com.simicaleksandar.radar.model.inner.ViewHolderGroupedClasses;
import com.simicaleksandar.radar.validation.Validator;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import radar.AdapterFactory;
import radar.RecyclerViewAdapter;
import radar.ViewHolder;

@AutoService(Processor.class)
public class RadarProcessor extends AbstractProcessor {

  private Types typeUtils;
  private Elements elementUtils;
  private Filer filer;
  private ViewHolderGroupedClasses viewHolderGroupedClasses;
  private AdapterFactoryGroupedInterfaces adapterFactoryGroupedInterfaces;
  private RecyclerViewAdapterGroupedMethods recyclerViewAdapterGroupedMethods;

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
    boolean hasErrors = processViewHoldersAnnotations(roundEnv);
    if (hasErrors) return true;

    hasErrors = processRecyclerViewAdapterAnnotations(roundEnv);
    if (hasErrors) return true;

    hasErrors = processAdapterFactoryAnnotations(roundEnv);
    if (hasErrors) return true;

    hasErrors = brewJava();
    if (hasErrors) return true;

    return false;
  }

  private boolean processViewHoldersAnnotations(RoundEnvironment roundEnv) {
    boolean hasErrors = false;
    viewHolderGroupedClasses = new ViewHolderGroupedClasses("");
    for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(ViewHolder.class)) {
      try {
        ViewHolderAnnotatedClass annotatedViewHolder = parseViewHolderElement(annotatedElement);
        viewHolderGroupedClasses.add(annotatedViewHolder);
      } catch (ValidationException e) {
        Logger.error(annotatedElement, e.getLocalizedMessage());
        hasErrors = true;
      }
    }

    return hasErrors;
  }

  private ViewHolderAnnotatedClass parseViewHolderElement(Element annotatedElement) throws
          ValidationException {
    ViewHolderAnnotatedClass viewHolderAnnotatedClass = new
            ViewHolderAnnotatedClass(annotatedElement, typeUtils, elementUtils);
    Validator.newInstance(elementUtils, typeUtils).validate(viewHolderAnnotatedClass);
    return viewHolderAnnotatedClass;
  }

  private boolean processRecyclerViewAdapterAnnotations(RoundEnvironment roundEnv) {
    boolean hasErrors = false;
    recyclerViewAdapterGroupedMethods = new RecyclerViewAdapterGroupedMethods("");
    for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(RecyclerViewAdapter.class)) {
      try {
        RecyclerViewAdapterAnnotatedMethod annotatedMethod =
                parseRecyclerViewAdapterElement(annotatedElement);
        recyclerViewAdapterGroupedMethods.add(annotatedMethod);
      } catch (ValidationException | MethodNameAlreadyUsedException e) {
        Logger.error(annotatedElement, e.getLocalizedMessage());
        hasErrors = true;
      }
    }

    return hasErrors;
  }

  private RecyclerViewAdapterAnnotatedMethod parseRecyclerViewAdapterElement(
          Element annotatedElement) throws ValidationException {
    RecyclerViewAdapterAnnotatedMethod annotatedMethod = new
            RecyclerViewAdapterAnnotatedMethod(annotatedElement, elementUtils);
    Validator.newInstance(elementUtils, typeUtils).validate(annotatedMethod);
    return annotatedMethod;
  }

  private boolean processAdapterFactoryAnnotations(RoundEnvironment roundEnv) {
    boolean hasErrors = false;
    adapterFactoryGroupedInterfaces = new AdapterFactoryGroupedInterfaces("");
    for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(AdapterFactory.class)) {
      try {
        AdapterFactoryAnnotatedInterface annotatedAdapterFactory =
                parseAdapterFactoryElement(annotatedElement);
        adapterFactoryGroupedInterfaces.add(annotatedAdapterFactory);
      } catch (ValidationException | QualifiedNameAlreadyUsedException e) {
        Logger.error(annotatedElement, e.getLocalizedMessage());
        hasErrors = true;
      }
    }

    return hasErrors;
  }

  private AdapterFactoryAnnotatedInterface parseAdapterFactoryElement(Element annotatedElement) throws
          ValidationException {
    AdapterFactoryAnnotatedInterface adapterFactoryAnnotatedInterface = new
            AdapterFactoryAnnotatedInterface(annotatedElement);
    Validator.newInstance(elementUtils, typeUtils).validate(adapterFactoryAnnotatedInterface);
    return adapterFactoryAnnotatedInterface;
  }

  private boolean brewJava() {
    boolean hasErrors = false;
    ViewHolderGroupGenerator viewHolderGroupGenerator = new ViewHolderGroupGenerator();
    List<JavaFile> adapterDelegatesFiles = viewHolderGroupGenerator
            .brewJava(viewHolderGroupedClasses);
    hasErrors = printJavaFiles(adapterDelegatesFiles);
    return hasErrors;
  }

  private boolean printJavaFiles(List<JavaFile> javaFiles) {
    boolean hasErrors = false;
    if (javaFiles == null) return hasErrors;

    for (JavaFile javaFile : javaFiles) {
      hasErrors = printJavaFile(javaFile);
    }

    return hasErrors;
  }

  private boolean printJavaFile(JavaFile javaFile) {
    boolean hasErrors = false;
    if (javaFile == null) return hasErrors;

    try {
      javaFile.writeTo(filer);
    } catch (IOException e) {
      hasErrors = true;
      Logger.error(e.getLocalizedMessage());
    }

    return hasErrors;
  }
}
