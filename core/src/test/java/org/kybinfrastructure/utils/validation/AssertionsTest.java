package org.kybinfrastructure.utils.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class AssertionsTest {

	@Test
	void notNull_Throws_IllegalArgumentException_When_GivenValue_Is_Null() {
		// given
		Object value = null;

		// when
		IllegalArgumentException expectedException = assertThrows(IllegalArgumentException.class,
				() -> Assertions.notNull(value, "cannot be null!"));

		// then
		assertEquals("cannot be null!", expectedException.getMessage());
	}

	@Test
	void notNull_DoesNot_Throw_IllegalArgumentException_When_GivenValue_Is_Not_Null() {
		// given
		Object value = new Object();

		// when & then
		assertDoesNotThrow(() -> Assertions.notNull(value, "cannot be null!"));
	}

	@Test
	void notBlank_Throws_IllegalArgumentException_When_GivenValue_Is_Null() {
		// given
		String value = null;

		// when
		IllegalArgumentException expectedException = assertThrows(IllegalArgumentException.class,
				() -> Assertions.notBlank(value, "cannot be blank!"));

		// then
		assertEquals("cannot be blank!", expectedException.getMessage());
	}

	@Test
	void notBlank_Throws_IllegalArgumentException_When_GivenValue_Has_No_NonSpace_Character() {
		// given
		String value = " ";

		// when
		IllegalArgumentException expectedException = assertThrows(IllegalArgumentException.class,
				() -> Assertions.notBlank(value, "cannot be blank!"));

		// then
		assertEquals("cannot be blank!", expectedException.getMessage());
	}

	@Test
	void notBlank_DoesNot_Throw_IllegalArgumentException_When_GivenValue_Has_NonSpace_Character() {
		// given
		String value = " a ";

		// when & then
		assertDoesNotThrow(() -> Assertions.notBlank(value, "cannot be blank!"));
	}

	@Test
	void notEmpty_Throws_IllegalArgumentException_When_GivenValue_Is_Null() {
		// given
		List<Object> value = null;

		// when
		IllegalArgumentException expectedException = assertThrows(IllegalArgumentException.class,
				() -> Assertions.notEmpty(value, "cannot be null!"));

		// then
		assertEquals("cannot be null!", expectedException.getMessage());
	}

	@Test
	void notEmpty_Throws_IllegalArgumentException_When_Given_NonNull_Collection_Has_No_Element() {
		// given
		List<Object> value = new ArrayList<>();

		// when
		IllegalArgumentException expectedException = assertThrows(IllegalArgumentException.class,
				() -> Assertions.notEmpty(value, "cannot be empty!"));

		// then
		assertEquals("cannot be empty!", expectedException.getMessage());
	}

	@Test
	void notEmpty_DoesNot_Throw_IllegalArgumentException_When_GivenCollection_Has_One_Element_At_Least() {
		// given
		List<Object> value = List.of(new Object());

		// when & then
		assertDoesNotThrow(() -> Assertions.notEmpty(value, "cannot be empty!"));
	}

}
