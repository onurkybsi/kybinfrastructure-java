package org.kybinfrastructure.exceptions;

/**
 * Base runtime exception type of <i>KybInfrastructure</i> framework
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
