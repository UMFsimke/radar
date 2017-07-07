package com.simicaleksandar.radar.exceptions;

public class ValidationException extends Exception {

  public ValidationException(String msg, Object... args) {
    super(String.format(msg, args));
  }
}
