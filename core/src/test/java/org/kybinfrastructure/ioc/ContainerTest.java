package org.kybinfrastructure.ioc;

class ContainerTest {

    // @Test
    // void container_Constructs_ManagedClasses_By_SortingThem_By_ConstructorParamNumber() {
    // // given
    // ManagedClass managedClassF =
    // new ManagedClass(TestClassF.class, TestClassF.class.getDeclaredConstructors()[0]);
    // ManagedClass managedClassC =
    // new ManagedClass(TestClassC.class, TestClassC.class.getDeclaredConstructors()[0]);
    // ManagedClass managedClassA =
    // new ManagedClass(TestClassA.class, TestClassA.class.getDeclaredConstructors()[0]);

    // // when
    // Container underTest =
    // new Container(new HashSet<>(List.of(managedClassF, managedClassC, managedClassA)));

    // // then
    // List<Class<?>> managedClasses = new ArrayList<>(underTest.getManagedClasses());
    // assertEquals(3, managedClasses.size());
    // assertEquals(TestClassA.class, managedClasses.get(0));
    // assertEquals(TestClassC.class, managedClasses.get(1));
    // assertEquals(TestClassF.class, managedClasses.get(2));
    // }

    // @Test
    // void init_Initializes_ManagedInstances() throws Exception {
    // // given
    // ManagedClass managedClassF =
    // new ManagedClass(TestClassF.class, TestClassF.class.getDeclaredConstructors()[0]);
    // ManagedClass managedClassC =
    // new ManagedClass(TestClassC.class, TestClassC.class.getDeclaredConstructors()[0]);
    // ManagedClass managedClassA =
    // new ManagedClass(TestClassA.class, TestClassA.class.getDeclaredConstructors()[0]);
    // Container underTest =
    // new Container(new HashSet<>(List.of(managedClassF, managedClassC, managedClassA)));

    // // when
    // underTest.init();

    // // then
    // assertEquals(3, underTest.getManagedClasses().size());

    // TestClassA managedTestClassAInstance = underTest.get(TestClassA.class);
    // assertNotNull(managedTestClassAInstance);

    // TestClassC managedTestClassCInstance = underTest.get(TestClassC.class);
    // assertNotNull(managedTestClassCInstance);
    // Field testClassAFieldOfTestClassC = TestClassC.class.getDeclaredField("testClassA");
    // testClassAFieldOfTestClassC.setAccessible(true);
    // assertEquals(managedTestClassAInstance,
    // testClassAFieldOfTestClassC.get(managedTestClassCInstance));

    // TestClassF managedTestClassFInstance = underTest.get(TestClassF.class);
    // assertNotNull(managedTestClassFInstance);
    // Field testClassAFieldOfTestClassF = TestClassF.class.getDeclaredField("testClassA");
    // testClassAFieldOfTestClassF.setAccessible(true);
    // Field testClassCFieldOfTestClassF = TestClassF.class.getDeclaredField("testClassC");
    // testClassCFieldOfTestClassF.setAccessible(true);
    // assertEquals(managedTestClassAInstance,
    // testClassAFieldOfTestClassF.get(managedTestClassFInstance));
    // assertEquals(managedTestClassCInstance,
    // testClassCFieldOfTestClassF.get(managedTestClassFInstance));
    // }

    // @Test
    // @SuppressWarnings({"java:S5845"})
    // void
    // init_Throws_UnexpectedException_When_Unexpected_Exception_Occurred_During_Initialization()
    // throws Exception {
    // // given
    // ManagedClass managedClassA =
    // new ManagedClass(TestClassA.class, TestClassA.class.getDeclaredConstructors()[0]);
    // Container underTest = new Container(new HashSet<>(List.of(managedClassA)));
    // Field managedInstancesField = Container.class.getDeclaredField("managedInstances");
    // managedInstancesField.setAccessible(true);
    // managedInstancesField.set(underTest, null);

    // // when
    // UnexpectedException thrownException =
    // assertThrows(UnexpectedException.class, () -> underTest.init());

    // // then
    // assertEquals("Initialization is not successful!", thrownException.getMessage());
    // assertEquals(NullPointerException.class, thrownException.getCause().getClass());
    // }

    // @Test
    // void get_Returns_ManagedInstance_By_GivenClassInstance() throws Exception {
    // // given
    // ManagedClass managedClassF =
    // new ManagedClass(TestClassF.class, TestClassF.class.getDeclaredConstructors()[0]);
    // ManagedClass managedClassC =
    // new ManagedClass(TestClassC.class, TestClassC.class.getDeclaredConstructors()[0]);
    // ManagedClass managedClassA =
    // new ManagedClass(TestClassA.class, TestClassA.class.getDeclaredConstructors()[0]);
    // Container underTest =
    // new Container(new HashSet<>(List.of(managedClassF, managedClassC, managedClassA))).init();

    // // when
    // TestClassF actualResult = underTest.get(TestClassF.class);

    // // then
    // assertNotNull(actualResult);
    // Field testClassAFieldOfTestClassF = TestClassF.class.getDeclaredField("testClassA");
    // testClassAFieldOfTestClassF.setAccessible(true);
    // Field testClassCFieldOfTestClassF = TestClassF.class.getDeclaredField("testClassC");
    // testClassCFieldOfTestClassF.setAccessible(true);
    // assertEquals(underTest.get(TestClassA.class), testClassAFieldOfTestClassF.get(actualResult));
    // assertEquals(underTest.get(TestClassC.class), testClassCFieldOfTestClassF.get(actualResult));
    // }

    // @Test
    // void get_Returns_Null_When_NoManagedInstance_Found_By_GivenClassInstance() {
    // // given
    // ManagedClass managedClassA =
    // new ManagedClass(TestClassA.class, TestClassA.class.getDeclaredConstructors()[0]);
    // Container underTest = new Container(new HashSet<>(List.of(managedClassA))).init();

    // // when
    // var actualResult = underTest.get(TestClassC.class);

    // // then
    // assertNull(actualResult);
    // }

    // @Test
    // void get_Throws_IllegalArgumentException_When_GivenClassInstance_IsNull() {
    // // given
    // Container underTest = new Container(new HashSet<>(
    // List.of(new ManagedClass(TestClassA.class, TestClassA.class.getDeclaredConstructors()[0]))))
    // .init();

    // // when
    // IllegalArgumentException thrownException =
    // assertThrows(IllegalArgumentException.class, () -> underTest.get(null));

    // // then
    // assertEquals("classInstance cannot be null!", thrownException.getMessage());
    // }

    // @Test
    // void getManagedClasses_Returns_ManagedClassInstances() {
    // // given
    // ManagedClass managedClassF =
    // new ManagedClass(TestClassF.class, TestClassF.class.getDeclaredConstructors()[0]);
    // ManagedClass managedClassC =
    // new ManagedClass(TestClassC.class, TestClassC.class.getDeclaredConstructors()[0]);
    // ManagedClass managedClassA =
    // new ManagedClass(TestClassA.class, TestClassA.class.getDeclaredConstructors()[0]);
    // Container underTest =
    // new Container(new HashSet<>(List.of(managedClassF, managedClassC, managedClassA)));

    // // when
    // var actualResult = underTest.getManagedClasses();

    // // then
    // assertEquals(3, actualResult.size());
    // assertTrue(actualResult.stream()
    // .anyMatch(mc -> mc.getName().equals("org.kybinfrastructure.ioc.test_classes.TestClassF")));
    // assertTrue(actualResult.stream()
    // .anyMatch(mc -> mc.getName().equals("org.kybinfrastructure.ioc.test_classes.TestClassC")));
    // assertTrue(actualResult.stream()
    // .anyMatch(mc -> mc.getName().equals("org.kybinfrastructure.ioc.test_classes.TestClassA")));
    // }

}
