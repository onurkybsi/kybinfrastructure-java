package org.kybinfrastructure.utils.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Contains unit test of the component {@link Assertions}
 */
class AssertionsTest {

	@Test
	void notNull_Should_Throw_IllegalArgumentException_When_Given_Value_Is_Null() {
		// given
		Object value = null;

		// when
		IllegalArgumentException expectedException = assertThrows(IllegalArgumentException.class,
				() -> Assertions.notNull(value, "cannot be null!"));

		// then
		assertEquals("cannot be null!", expectedException.getMessage());
	}

	@Test
	void notNull_Should_Not_Throw_IllegalArgumentException_When_Given_Value_Is_Not_Null() {
		// given
		Object value = new Object();

		// when & then
		assertDoesNotThrow(() -> Assertions.notNull(value, "cannot be null!"));
	}

	@Test
	void notBlank_Should_Throw_IllegalArgumentException_When_Given_Value_Is_Null() {
		// given
		String value = null;

		// when
		IllegalArgumentException expectedException = assertThrows(IllegalArgumentException.class,
				() -> Assertions.notBlank(value, "cannot be blank!"));

		// then
		assertEquals("cannot be blank!", expectedException.getMessage());
	}

	@Test
	void notBlank_Should_Throw_IllegalArgumentException_When_Given_Value_Has_No_Non_Space_Character() {
		// given
		String value = null;

		// when
		IllegalArgumentException expectedException = assertThrows(IllegalArgumentException.class,
				() -> Assertions.notBlank(value, "cannot be blank!"));

		// then
		assertEquals("cannot be blank!", expectedException.getMessage());
	}

	@Test
	void notBlank_Should_Not_Throw_IllegalArgumentException_When_Given_Value_Has_Non_Space_Character() {
		// given
		String value = " a ";

		// when & then
		assertDoesNotThrow(() -> Assertions.notBlank(value, "cannot be blank!"));
	}
}
