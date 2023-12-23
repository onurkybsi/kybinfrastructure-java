package org.kybinfrastructure.utils.validation;

import java.util.regex.Pattern;

/**
 * Contains useful validation methods.
 */
public final class ValidationUtils {

	private static final Pattern REGEX_UUID = Pattern
			.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
	private static final Pattern REGEX_EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

	private ValidationUtils() {}

	/**
	 * Validates whether the given value is a UUID or not.
	 * 
	 * @param value value to be validated
	 * @return {@code true} if given {@code value} is a UUID, otherwise {@code false}
	 */
	public static boolean isUuid(String value) {
		if (value == null) {
			return false;
		}
		return REGEX_UUID.matcher(value).matches();
	}

	/**
	 * Validates whether the given value is an e-mail or not.
	 * 
	 * @param value value to be validated
	 * @return {@code true} if given {@code value} is an e-mail, otherwise {@code false}
	 */
	public static boolean isEmail(String value) {
		if (value == null) {
			return false;
		}
		return REGEX_EMAIL.matcher(value).matches();
	}

	/**
	 * Validates whether the given {@code String} value contains at least one non-space character or
	 * not.
	 * 
	 * @param value to be validated
	 * @return {@code true} if given {@code value} has no non-space character, otherwise {@code false}
	 */
	public static boolean isBlank(String value) {
		return value == null || value.trim().length() == 0;
	}

}
