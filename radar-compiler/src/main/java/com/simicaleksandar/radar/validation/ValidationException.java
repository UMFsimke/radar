package com.simicaleksandar.radar.validation;

class ValidationException extends Exception {

  ValidationException(String msg, Object... args) {
    super(String.format(msg, args));
  }
}
