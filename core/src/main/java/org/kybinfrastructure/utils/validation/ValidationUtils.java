package org.kybinfrastructure.utils.validation;

import java.util.regex.Pattern;

/**
 * Contains useful validation methods
 */
public final class ValidationUtils {
	private static final Pattern UUID_REGEX = Pattern
			.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
	private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

	private ValidationUtils() {}

	/**
	 * Checks whether the given value represents UUID or not
	 * 
	 * @param value value to be checked
	 * @return {@code true} if the given {@code value} represents UUID, otherwise {@code false}
	 */
	public static boolean isUuid(String value) {
		if (value == null) {
			return false;
		}
		return UUID_REGEX.matcher(value).matches();
	}

	/**
	 * Simply checks whether the given value is email formatted or not
	 * 
	 * @param value value to be checked
	 * @return {@code true} if the given {@code value} is an email, otherwise {@code false}
	 */
	public static boolean isEmail(String value) {
		if (value == null) {
			return false;
		}
		return EMAIL_REGEX.matcher(value).matches();
	}

	/**
	 * Checks whether the given {@code String} value contains at least one non space character.
	 * 
	 * @param value to be checked
	 * @return {@code true} if the given {@code value} has no non space character, otherwise
	 *         {@code false}
	 */
	public static boolean isBlank(String value) {
		return value == null || value.trim().length() == 0;
	}

}
