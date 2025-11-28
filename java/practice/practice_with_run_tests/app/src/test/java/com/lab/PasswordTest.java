package com.lab;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class for Password implementations.
 * 
 * To test different buggy versions, simply uncomment the corresponding
 * getPassword() method and comment out the others.
 * 
 * Available implementations:
 * - Password: Correct implementation
 * - BugDoesNotTrim: Does not trim whitespace
 * - BugToShortPassword: Allows passwords shorter than 12 characters
 * - BugVeryShort: Allows way to short passwords
 * - BugWrongExceptionMessage: Wrong exception message for short passwords
 * - BugMissingPasswordLengthCheck: Does not throw exception for short passwords
 * - BugMissingNumberCheck: Does not throw exception if password lacks a number
 * - BugIsPasswordSameAlwaysTrue: isPasswordSame always returns true
 * - BugWrongHashingAlgorithm: Wrong hashing algorithm
 */

public class PasswordTest {
    private IPassword getPassword(String s) throws Exception {

        return (IPassword) new Password(s);
        // return (IPassword) new BugDoesNotTrim(s);
        // return (IPassword) new BugToShortPassword(s);
        // return (IPassword) new BugVeryShort(s);
        // return (IPassword) new BugWrongExceptionMessage(s);
        // return (IPassword) new BugMissingPasswordLengthCheck(s);
        // return (IPassword) new BugMissingNumberCheck(s);
        // return (IPassword) new BugIsPasswordSameAlwaysTrue(s);
        // return (IPassword) new BugWrongHashingAlgorithm(s);
    }

    @Test
    public void shouldAlwaysPass() throws Exception {
        assertTrue(true);
    }

    @Test
    public void shouldRejectPasswordToShort() throws Exception {
        assertThrows(Exception.class,() -> getPassword("d1dds"), "To short password");
    }

    @Test
    public void shouldRejectPasswordToShortWithWhiteSpace() throws Exception {
        assertThrows(Exception.class,() -> getPassword("      dss1ddd      "), "Passwords has leading or trailing white spaces.");
    }

    @Test
    public void shouldRejectPasswordWithoutNumber() {
        assertThrows(Exception.class,() -> getPassword("dsadddsdadsaa"));
    }

    @Test
    public void shouldRejectPasswordOneBeneathLimit() throws Exception {
        assertThrows(Exception.class,() -> getPassword("dsaad1s1ass"), "To short password");
    }

    @Test
    public void shouldReturnCorrectExceptionMessageForToShortPassword() throws Exception {
        Exception thrown = assertThrows(Exception.class, () -> getPassword("dsa2das"));

        assertEquals("To short password", thrown.getMessage());
    }
    
    @Test
    public void shouldReturnIfPasswordIsSame() throws Exception {

        
        var c = getPassword("dsasada22131dasdas");
        var t = getPassword("dsasada22131dasdas");

        assertTrue(() -> c.isPasswordSame(t));


    }

    @Test
    public void shouldRejectIfPasswordIsNotSame() throws Exception {
        var c = getPassword("dsasada22131dasdas");
        var t = getPassword("dsasada21dasdas");

        assertFalse(() -> c.isPasswordSame(t));
    }
}
