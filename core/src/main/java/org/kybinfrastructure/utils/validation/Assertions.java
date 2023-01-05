package org.kybinfrastructure.utils.validation;

/**
 * Contains useful assertion methods
 */
public final class Assertions {
	private Assertions() {}

	/**
	 * Checks whether the given object is not null.
	 * 
	 * @param value value to be checked
	 * @param message message to be set as the {@link IllegalArgumentException} message if the given
	 *        {@code value} is {@code null}
	 * @throws IllegalArgumentException is thrown when the given {@code value} is {@code null}
	 */
	public static void notNull(Object value, String message) {
		if (value == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Checks whether the given {@code String} value contains at least one non space character.
	 * 
	 * @param value {@code String} value to be checked
	 * @param message message to be set as the {@link IllegalArgumentException} message if the given
	 *        {@code value} has no non space character
	 * @throws IllegalArgumentException is thrown when the given {@code value} has no non space
	 *         character
	 */
	public static void notBlank(String value, String message) {
		if (value == null) {
			throw new IllegalArgumentException(message);
		}
		if (value.trim().length() == 0) {
			throw new IllegalArgumentException(message);
		}
	}
}
