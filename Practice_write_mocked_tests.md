# Practice Writing and Running Tests with mocks
Most of the assignment works as the previous practice "run test" but now we have two classes with a dependency. We want to test these classes independently.

The previous task had private methods that normally cannot be tested directly. In this task we have extracted the private methods into a helper class where they are public. The two classes now needs to be tested in a way so that a failing test shows which of the classes that contains the bug. 

SwedishSecurityNumber depends on the class SSNHelper who holds all the validation methods. A test for SwedishSecurityNumber should only test functionality in the SwedishSecurityNumber class, and not rely on SSNHelper. To accomplish this we can make use of "mocks". 

A mock is a configurable class instance that acts as a object of SSNHelper but we can control its output in the test. 
 * Jest have mocking built in 
 * For Java you need to install mockito.

## Goals
* You should be able to test classes with dependencies
* You should be able to write tests with mocks

## Course material to support
* Developer Testing - Chapter 7 - 13
* Testing framework/tool online manuals for your framework/tools
* Mocking framework/tool online manuals for your framework/tools
* Course materials week 1-3

## Task 2: Write an optimized test suite for two classes 


* Write a test suite for SwedishSecurityNumber class and another for the SSNHelper class
A bug in the SwedishSecurityNumber must not fail due to a bug in SSNHelper. 
* None of the tests in SwedishSecurityNumber should use instances of the SSNHelper class.
    * Test the Helper and SwedishSecurityNumber  classes independently of each other
        * Tests for SwedishSecurityNumber  should only fail if the bug is in SwedishSecurityNumber  class
        * Tests for Helper should only fail if the bug is in Helper class
* Only one expect/assert/verify per test unless motivated
* Show tests and bug fail matrix as in previous task
* Add additional tests to increase coverage, but only if each test has value
  * Show that the additional tests has value by creating a bug that fails that test!
  * At least one additional test should be added!

* Artifacts to show during examination
  * Table below
  * A test suite for SwedishSecurityNumber that uses mocking and not the SSNHelper class itself
  * A test suite for SSNHelper.
  * A test per buggy version.
  * A set of tests to make sure coverage is high
    * An additional test


### Table for checking test suite bug coverage
| SUT | Test | Correct SwedishSocialSecurityNumber | BuggySSNHelperWrongLength | Buggy Helper 2 | ... | |
| --- | ---| --- | --- | --- | --- | --- |
| SwedishSecurityNumber | Test name 1 | ✅ |  |  |  | | 
| SwedishSecurityNumber | Test name 2 | ✅ |  |  |  | | 
| SSNHelper  | Test name 3 |  | ✅ | ❌ |  | | |
| Coverage |  | 100% | 100% | 100% | | |



| Method                                      | SSNHelper | SwedishSocialSecurityNumber | BuggySSNHelperAllowDayUpTo30 | BuggySSNHelperAllowMonth0 | BuggySSNHelperIncorrectFormat | BuggySSNHelperIncorrectFormatFalse | BuggySSNHelperMessyLuhn | BuggySSNHelperWrongLength | BuggySwedishSocialSecurityNumberNoLenCheck | BuggySwedishSocialSecurityNumberNoLuhn | BuggySwedishSocialSecurityNumberNoTrim | BuggySwedishSocialSecurityNumberWrongYear |
|---------------------------------------------|-----------|------------------------------|-------------------------------|----------------------------|--------------------------------|-------------------------------------|--------------------------|----------------------------|--------------------------------------------|----------------------------------------|-----------------------------------------|-------------------------------------------|
| shouldAcceptValidSSN                        | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ |
| shouldRejectIncorrectLength                 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ |
| shouldTrimPassword                           | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ |
| shouldRejectInvalidYear                     | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ |
| shouldRejectIncorrectFormat                 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| shouldRejectIncorrectDay                    | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| shouldRejectIncorrectMonth                  | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| shouldRejectIncorrectLuhn                   | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ |
| shouldReturnHelperCorrectLength             | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| shouldRejectHelperIncorrectLengthToShort    | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| shouldRejectHelperIncorrectLengthToLong     | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ |
| shouldReturnHelperCorrectDay                | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| shouldRejectHelperIncorrectDay              | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| shouldReturnHelperCorrectMonth              | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| shouldRejectHelperIncorrectMonth            | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| shouldReturnHelperCorrectFormat             | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| shouldRejectHelperIncorrectFormat           | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| shouldReturnHelperIsCorrectLuhn             | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ |
| shouldRejectHelperIsIncorrectLuhn           | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Coverage | 100% | 96% | 100% | 100% | 100% | 100% | 97% | 100% | 96% | 96% | 96% | 89% |



