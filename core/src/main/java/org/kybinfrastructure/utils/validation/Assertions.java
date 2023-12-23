package org.kybinfrastructure.utils.validation;

import java.util.Collection;

/**
 * Contains useful assertion methods.
 */
public final class Assertions {

	private Assertions() {
		throw new UnsupportedOperationException("This class is not initiable!");
	}

	/**
	 * Asserts the given object value is not null.
	 * 
	 * @param value value to assert as non-null
	 * @param message message to be set as the {@link IllegalArgumentException} message
	 * @throws IllegalArgumentException when the given {@code value} is {@code null}
	 */
	public static void notNull(Object value, String message) {
		if (value == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Asserts the given {@code String} value contains at least one non-space character.
	 * 
	 * @param value {@code String} value to assert as not blank
	 * @param message message to be set as the {@link IllegalArgumentException} message
	 * @throws IllegalArgumentException when the given {@code value} has no non-space character
	 */
	public static void notBlank(String value, String message) {
		if (value == null) {
			throw new IllegalArgumentException(message);
		}
		if (value.trim().length() == 0) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Asserts the given {@code Collection} value contains at least one element.
	 * 
	 * @param value {@code Collection} value to assert as non-empty
	 * @param message message to be set as the {@link IllegalArgumentException} message
	 * @throws IllegalArgumentException when the given {@code value} has no element in the collection
	 */
	public static void notEmpty(Collection<?> value, String message) {
		if (value == null) {
			throw new IllegalArgumentException(message);
		}
		if (value.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}

}
