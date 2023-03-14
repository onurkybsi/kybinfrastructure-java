package org.kybinfrastructure.exception;

/**
 * {@link KybInfrastructureException} exception which is used for the data which cannot be found.
 * 
 * @author Onur Kayabasi (onurbpm@outlook.com)
 */
public class NotFoundException extends KybInfrastructureException {

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotFoundException(String format, Object... arguments) {
    super(format, arguments);
  }

}
