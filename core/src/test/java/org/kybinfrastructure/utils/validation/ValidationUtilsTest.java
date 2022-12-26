package org.kybinfrastructure.utils.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.UUID;

/**
 * Contains unit test of the component {@link ValidationUtils}
 */
class ValidationUtilsTest {

	@Test
	void isUuid_Should_Return_False_When_Given_Value_Is_Null() {
		// given
		String value = null;

		// when
		boolean actualResult = ValidationUtils.isUuid(value);

		// then
		assertFalse(actualResult);
	}

	@Test
	void isUuid_Should_Return_False_When_Given_Value_Does_Not_Represent_Uuid() {
		// given
		String value = "non_uuid";

		// when
		boolean actualResult = ValidationUtils.isUuid(value);

		// then
		assertFalse(actualResult);
	}

	@Test
	void isUuid_Should_Return_True_When_Given_Value_Represents_Uuid() {
		// given
		String value = UUID.randomUUID().toString();

		// when
		boolean actualResult = ValidationUtils.isUuid(value);

		// then
		assertTrue(actualResult);
	}

	@Test
	void isEmail_Should_Return_False_When_Given_Value_Is_Null() {
		// given
		String value = null;

		// when
		boolean actualResult = ValidationUtils.isEmail(value);

		// then
		assertFalse(actualResult);
	}

	@Test
	void isEmail_Should_Return_False_When_Given_Value_Is_Not_Email_Formatted() {
		// given
		String value = "not_email";

		// when
		boolean actualResult = ValidationUtils.isEmail(value);

		// then
		assertFalse(actualResult);
	}

	@Test
	void isEmail_Should_Return_True_When_Given_Value_Is_Email_Formatted() {
		// given
		String value = "onurbpm@outlook.com";

		// when
		boolean actualResult = ValidationUtils.isEmail(value);

		// then
		assertTrue(actualResult);
	}

	@Test
	void isBlank_Should_Return_True_When_Given_Value_Is_Null() {
		// given
		String value = null;

		// when
		boolean actualResult = ValidationUtils.isBlank(value);

		// then
		assertTrue(actualResult);
	}

	@Test
	void isBlank_Should_Return_True_When_Given_Value_Has_No_Non_Space_Character() {
		// given
		String value = null;

		// when
		boolean actualResult = ValidationUtils.isBlank(value);

		// then
		assertTrue(actualResult);
	}

	@Test
	void isBlank_Should_Return_False_When_Given_Value_Has_Non_Space_Characters() {
		// given
		String value = " a ";

		// when
		boolean actualResult = ValidationUtils.isBlank(value);

		// then
		assertFalse(actualResult);
	}

}
