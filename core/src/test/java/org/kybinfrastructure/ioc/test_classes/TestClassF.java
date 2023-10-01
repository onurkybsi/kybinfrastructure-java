package org.kybinfrastructure.ioc.test_classes;

public class TestClassF {

  private final TestClassA testClassA;
  private final TestClassC testClassC;

  public TestClassF(TestClassA testClassA, TestClassC testClassC) {
    this.testClassA = testClassA;
    this.testClassC = testClassC;
  }

}
