package org.kybinfrastructure.ioc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.kybinfrastructure.exception.KybInfrastructureException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/*
 * Unit tests for the component {@link ScannerImpl}
 */
class ScannerImplTest {

	private ScannerImpl underTest = new ScannerImpl();

	@Test
	void scan_ThrowsIllegalArgumentException_IfGivenRootClassIsNull() {
		// given
		Class<?> rootClass = null;

		// when
		IllegalArgumentException thrownException =
				assertThrows(IllegalArgumentException.class, () -> underTest.scan(rootClass));

		// then
		assertEquals("rootClass cannot be null!", thrownException.getMessage());
	}

	@Test
	void scan_ReturnsAllClassInstancesOfClassesInThePathOfRootClassOrUnderThePathOfRootClass() {
		// given
		Class<?> rootClass = ScannerImplTest.class;

		// when
		Set<Class<?>> actualResult = underTest.scan(rootClass);

		// then
		assertEquals(2, actualResult.size());
		assertTrue(actualResult.stream().anyMatch(c -> ScannerImplTest.class.equals(c)));
		assertTrue(actualResult.stream()
				.anyMatch(c -> c.getSimpleName().equals("InnerClassToCauseException")));
	}

	@Test
	void scan_ThrowsKybInfrastructureException_IfSomethingWentWrongDuringScanning()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {
		// given
		class InnerClassToCauseException {
		}
		Class<?> rootClass = InnerClassToCauseException.class;

		// when
		KybInfrastructureException thrownException =
				assertThrows(KybInfrastructureException.class, () -> underTest.scan(rootClass));

		// then
		assertEquals(String.format("Scanning is not successful & rootClass: %s",
				InnerClassToCauseException.class.getName()), thrownException.getMessage());
	}

	@ParameterizedTest
	@MethodSource("invalidScanningParameterProvider")
	void scan_ThrowsIllegalArgumentException_IfGivenParametersAreNotValid(Class<?> rootClass,
			Predicate<Class<?>> filter, String expectedExceptionMessage) {
		// given & when
		IllegalArgumentException thrownException =
				assertThrows(IllegalArgumentException.class, () -> underTest.scan(rootClass, filter));

		// then
		assertEquals(thrownException.getMessage(), expectedExceptionMessage);
	}

	private static Stream<Arguments> invalidScanningParameterProvider() {
		return Stream.of(
				Arguments.of(null, (Predicate<Class<?>>) ((c) -> true), "rootClass cannot be null!"),
				Arguments.of(ScannerImplTest.class, null, "filter cannot be null!"));
	}

	@Test
	void scan_ReturnsClassInstancesOfClassesInThePathOfRootClassOrUnderThePathOfRootClassByFilteringByGivenFilter() {
		// given
		Class<?> rootClass = ScannerImplTest.class;

		// when
		Set<Class<?>> actualResult = underTest.scan(rootClass, c -> !c.isLocalClass());

		// then
		assertEquals(1, actualResult.size());
		assertTrue(actualResult.stream().anyMatch(c -> ScannerImplTest.class.equals(c)));
	}

}
