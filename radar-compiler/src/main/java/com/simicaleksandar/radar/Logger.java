package com.simicaleksandar.radar;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

class Logger {

  private static Logger instance;
  private Messager messager;

  public static Logger init(ProcessingEnvironment processingEnv) {
    if (instance == null) {
      instance = new Logger(processingEnv);
    }

    return instance;
  }

  private Logger(ProcessingEnvironment processingEnv) {
    messager = processingEnv.getMessager();
  }

  public static void error(Element e, String errorMsg) {
    instance.error(e, errorMsg);
  }

  private void logError(Element e, String errorMsg) {
    messager.printMessage(Diagnostic.Kind.ERROR, errorMsg, e);
  }
}
