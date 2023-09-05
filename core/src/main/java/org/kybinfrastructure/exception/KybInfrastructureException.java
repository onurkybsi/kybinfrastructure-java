package org.kybinfrastructure.exception;

/**
 * Base runtime exception of <i>KybInfrastructure</i> framework.
 * 
 * @author Onur Kayabasi (o.kayabasi@outlook.com)
 */
public class KybInfrastructureException extends RuntimeException {

	public KybInfrastructureException(String message) {
		super(message);
	}

	public KybInfrastructureException(String message, Throwable cause) {
		super(message, cause);
	}

	public KybInfrastructureException(String format, Object... arguments) {
		super(String.format(format, arguments));
	}

}
