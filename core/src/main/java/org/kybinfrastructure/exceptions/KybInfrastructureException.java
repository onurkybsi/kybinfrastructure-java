package org.kybinfrastructure.exceptions;

public class KybInfrastructureException extends RuntimeException {
	public KybInfrastructureException(String message) {
		super(message);
	}

	public KybInfrastructureException(String message, Throwable cause) {
		super(message, cause);
	}
}
