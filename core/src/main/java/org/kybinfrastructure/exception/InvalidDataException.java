package org.kybinfrastructure.exception;

/**
 * Represents thrown exceptions because of unexpected data invalidity.
 * 
 * @author Onur Kayabasi (onurbpm@outlook.com)
 */
public class InvalidDataException extends KybInfrastructureException {

  public InvalidDataException(String message) {
    super(message);
  }

  public InvalidDataException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidDataException(String format, Object... arguments) {
    super(format, arguments);
  }

}
