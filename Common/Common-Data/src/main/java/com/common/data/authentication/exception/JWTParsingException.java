package com.common.data.authentication.exception;

public class JWTParsingException extends RuntimeException {
  public JWTParsingException(String message) {
    super(message);
  }
}