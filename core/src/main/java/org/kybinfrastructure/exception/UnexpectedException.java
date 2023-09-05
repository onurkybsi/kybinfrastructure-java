package org.kybinfrastructure.exception;

/**
 * {@link KybInfrastructureException} exception which is used for the cases which are totally not
 * excepted.
 * 
 * @author Onur Kayabasi (o.kayabasi@outlook.com)
 */
public class UnexpectedException extends KybInfrastructureException {

  public UnexpectedException(String message) {
    super(message);
  }

  public UnexpectedException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnexpectedException(String format, Object... arguments) {
    super(format, arguments);
  }

}
