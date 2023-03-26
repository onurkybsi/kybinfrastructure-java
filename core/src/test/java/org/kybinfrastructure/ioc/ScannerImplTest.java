package org.kybinfrastructure.ioc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import example.service.AnotherService;
import example.service.SomeService;

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
	void scan_ReturnsAllClassInstancesOfClassesInThePathOfRootClassOrInTheSubPathsOfRootClass() {
		// given
		Class<?> rootClass = SomeService.class;

		// when
		Set<Class<?>> actualResult = underTest.scan(rootClass);

		// then
		assertEquals(4, actualResult.size());
		assertTrue(actualResult.stream().anyMatch(c -> SomeService.class.equals(c)));
		assertTrue(actualResult.stream()
				.anyMatch(c -> Stream.of(c.getInterfaces()).anyMatch(i -> SomeService.class.equals(i))));
		assertTrue(actualResult.stream().anyMatch(c -> AnotherService.class.equals(c)));
		assertTrue(actualResult.stream()
				.anyMatch(c -> Stream.of(c.getInterfaces()).anyMatch(i -> AnotherService.class.equals(i))));
	}

	@Test
	void scanWithFilter_ThrowsIllegalArgumentException_IfGivenRootClassIsNull() {
		// given
		Class<?> rootClass = null;
		Predicate<Class<?>> filter = (Predicate<Class<?>>) ((c) -> true);

		// when
		IllegalArgumentException thrownException =
				assertThrows(IllegalArgumentException.class, () -> underTest.scan(rootClass, filter));

		// then
		assertEquals("rootClass cannot be null!", thrownException.getMessage());
	}

	@Test
	void scanWithFilter_ThrowsIllegalArgumentException_IfGivenFilterIsNull() {
		// given
		Class<?> rootClass = ScannerImplTest.class;
		Predicate<Class<?>> filter = null;

		// when
		IllegalArgumentException thrownException =
				assertThrows(IllegalArgumentException.class, () -> underTest.scan(rootClass, filter));

		// then
		assertEquals("filter cannot be null!", thrownException.getMessage());
	}

	@Test
	void scanWithFilter_ReturnsClassInstancesOfClassesInThePathOfRootClassOrUnderThePathOfRootClassByFilteringByGivenFilter() {
		// given
		Class<?> rootClass = SomeService.class;

		// when
		Set<Class<?>> actualResult =
				underTest.scan(rootClass, c -> !c.getSimpleName().equals(SomeService.class.getSimpleName())
						&& !Stream.of(c.getInterfaces()).anyMatch(i -> SomeService.class.equals(i)));

		// then
		assertEquals(2, actualResult.size());
		assertTrue(actualResult.stream().anyMatch(c -> AnotherService.class.equals(c)));
	}

}
