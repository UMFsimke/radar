package com.simicaleksandar.radar.validation;

class ValidationResult {

  private static final boolean INVALID = false;
  private static final ValidationResult VALID = new ValidationResult(true, null);

  private boolean valid;
  private String error;

  static ValidationResult error(String msg, Object... args) {
    return new ValidationResult(INVALID, String.format(msg, args));
  }

  public ValidationResult valid() {
    return VALID;
  }

  private ValidationResult(boolean valid, String error) {
    this.valid = valid;
    this.error = error;
  }

  public boolean isValid() {
    return valid;
  }

  public String getError() {
    return error;
  }
}
