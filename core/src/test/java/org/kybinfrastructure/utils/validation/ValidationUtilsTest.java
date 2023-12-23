package org.kybinfrastructure.utils.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ValidationUtilsTest {

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = {"", "  ", "something_not_uuid"})
	void isUuid_Returns_False_When_GivenValue_Is_Not_UUID(String value) {
		// given

		// when
		boolean actualResult = ValidationUtils.isUuid(value);

		// then
		assertFalse(actualResult);
	}

	@Test
	void isUuid_Returns_True_When_GivenValue_Is_UUID() {
		// given
		String value = UUID.randomUUID().toString();

		// when
		boolean actualResult = ValidationUtils.isUuid(value);

		// then
		assertTrue(actualResult);
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = {"", "  ", "something_not_email@"})
	void isEmail_Returns_False_When_GivenValue_Is_Not_EMail(String value) {
		// given

		// when
		boolean actualResult = ValidationUtils.isEmail(value);

		// then
		assertFalse(actualResult);
	}

	@Test
	void isEmail_Returns_True_When_GivenValue_Is_Email() {
		// given
		String value = "o.kayabasi@outlook.com";

		// when
		boolean actualResult = ValidationUtils.isEmail(value);

		// then
		assertTrue(actualResult);
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = {"", "  "})
	void isBlank_Returns_True_When_GivenValue_Is_Blank(String value) {
		// given

		// when
		boolean actualResult = ValidationUtils.isBlank(value);

		// then
		assertTrue(actualResult);
	}

	@Test
	void isBlank_Returns_False_When_GivenValue_Has_NonSpace_Character() {
		// given
		String value = " a ";

		// when
		boolean actualResult = ValidationUtils.isBlank(value);

		// then
		assertFalse(actualResult);
	}

}
