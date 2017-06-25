package com.simicaleksandar.radar.validation;

public class ValidationException extends Exception {

  public ValidationException(String msg, Object... args) {
    super(String.format(msg, args));
  }
}
