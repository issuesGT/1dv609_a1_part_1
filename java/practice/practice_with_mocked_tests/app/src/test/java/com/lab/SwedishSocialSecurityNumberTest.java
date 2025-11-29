package com.lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SwedishSocialSecurityNumberTest {
    
    private SSNHelper helper;

    @BeforeEach
    public void setUp() {
        helper = new SSNHelper();
    }
    
    @Test
    public void shouldAcceptValidSSN() throws Exception {

        SSNHelper mock = mock(SSNHelper.class);
        
        when(mock.isCorrectLength("900101-0017")).thenReturn(true);
        when(mock.isCorrectFormat("900101-0017")).thenReturn(true);
        when(mock.isValidDay("01")).thenReturn(true);
        when(mock.isValidMonth("01")).thenReturn(true);
        when(mock.luhnIsCorrect("900101-0017")).thenReturn(true);

        SwedishSocialSecurityNumber ssn = new SwedishSocialSecurityNumber("900101-0017", mock);
        
        assertEquals("90", ssn.getYear());
        assertEquals("01", ssn.getMonth());
        assertEquals("01", ssn.getDay());
        assertEquals("0017", ssn.getSerialNumber());

        verify(mock).isCorrectLength("900101-0017");
        verify(mock).isCorrectFormat("900101-0017");
        verify(mock).isValidMonth("01");
        verify(mock).isValidDay("01");
        verify(mock).luhnIsCorrect("900101-0017");
    }

    @Test
    public void shouldRejectIncorrectLength() throws Exception {
        SSNHelper mock = mock(SSNHelper.class);

        when(mock.isCorrectLength("9001-0017")).thenReturn(false);

        Exception thrown = assertThrows(Exception.class,() -> new SwedishSocialSecurityNumber("9001-0017", mock));
        assertEquals("To short, must be 11 characters", thrown.getMessage());

        verify(mock).isCorrectLength("9001-0017");
    }

    @Test
    public void shouldTrimPassword() {
        SSNHelper mock = mock(SSNHelper.class);

        when(mock.isCorrectLength("900101-0017")).thenReturn(true);
        when(mock.isCorrectFormat("900101-0017")).thenReturn(true);
        when(mock.isValidDay("01")).thenReturn(true);
        when(mock.isValidMonth("01")).thenReturn(true);
        when(mock.luhnIsCorrect("900101-0017")).thenReturn(true);

        assertDoesNotThrow( () -> new SwedishSocialSecurityNumber(" 900101-0017 ", mock));


        verify(mock).isCorrectLength("900101-0017");
        verify(mock).isCorrectFormat("900101-0017");
        verify(mock).isValidDay("01");
        verify(mock).isValidMonth("01");
        verify(mock).luhnIsCorrect("900101-0017");        
    }

    @Test
    public void shouldRejectInvalidYear() {
        SSNHelper mock = mock(SSNHelper.class);

        when(mock.isCorrectLength("900101-0017")).thenReturn(true);
        when(mock.isCorrectFormat("900101-0017")).thenReturn(true);
        when(mock.isValidDay("01")).thenReturn(true);
        when(mock.isValidMonth("01")).thenReturn(true);
        when(mock.luhnIsCorrect("900101-0017")).thenReturn(true);

        var thrown = assertDoesNotThrow(() -> new SwedishSocialSecurityNumber("900101-0017", mock));

        assertEquals("90", thrown.getYear());

        verify(mock).isCorrectLength("900101-0017");
        verify(mock).isCorrectFormat("900101-0017");
        verify(mock).isValidDay("01");
        verify(mock).isValidMonth("01");
        verify(mock).luhnIsCorrect("900101-0017"); 
    }

    @Test
    public void shouldRejectIncorrectFormat() throws Exception {
        SSNHelper mock = mock(SSNHelper.class);
        
        when(mock.isCorrectLength("90010110017")).thenReturn(true);
        when(mock.isCorrectFormat("90010110017")).thenReturn(false);

        Exception thrown = assertThrows(Exception.class, () -> new SwedishSocialSecurityNumber("90010110017", mock));

        assertEquals("Incorrect format, must be: YYMMDD-XXXX", thrown.getMessage());

        verify(mock).isCorrectLength("90010110017");
        verify(mock).isCorrectFormat("90010110017");
    }

    @Test
    public void shouldRejectIncorrectDay() {
        SSNHelper mock = mock(SSNHelper.class);
        
        when(mock.isCorrectLength("900132-0017")).thenReturn(true);
        when(mock.isCorrectFormat("900132-0017")).thenReturn(true);
        when(mock.isValidMonth("01")).thenReturn(true);
        when(mock.isValidDay("32")).thenReturn(false);

        Exception thrown = assertThrows(Exception.class, () -> new SwedishSocialSecurityNumber("900132-0017", mock));

        assertEquals("Invalid day in SSN", thrown.getMessage());

        verify(mock).isCorrectLength("900132-0017");
        verify(mock).isCorrectFormat("900132-0017");
        verify(mock).isValidMonth("01");
        verify(mock).isValidDay("32");
    }

    @Test
    public void shouldRejectIncorrectMonth() {
        SSNHelper mock = mock(SSNHelper.class);
        
        when(mock.isCorrectLength("901301-0017")).thenReturn(true);
        when(mock.isCorrectFormat("901301-0017")).thenReturn(true);
        when(mock.isValidMonth("13")).thenReturn(false);

        Exception thrown = assertThrows(Exception.class, () -> new SwedishSocialSecurityNumber("901301-0017", mock));

        assertEquals("Invalid month in SSN", thrown.getMessage());

        verify(mock).isCorrectLength("901301-0017");
        verify(mock).isCorrectFormat("901301-0017");
        verify(mock).isValidMonth("13");
    }

    @Test
    public void shouldRejectIncorrectLuhn() throws Exception {
        SSNHelper mock = mock(SSNHelper.class);
        
        when(mock.isCorrectLength("90010110017")).thenReturn(true);
        when(mock.isCorrectFormat("90010110017")).thenReturn(true);
        when(mock.isValidMonth("01")).thenReturn(true);
        when(mock.isValidDay("01")).thenReturn(true);
        when(mock.luhnIsCorrect("900101-0017")).thenReturn(false);

        Exception thrown = assertThrows(Exception.class, () -> new SwedishSocialSecurityNumber("90010110017", mock));

        assertEquals("Invalid SSN according to Luhn's algorithm", thrown.getMessage());

        verify(mock).isCorrectLength("90010110017");
        verify(mock).isCorrectFormat("90010110017");
        verify(mock).isValidMonth("01");
        verify(mock).isValidDay("01");
        verify(mock).luhnIsCorrect("90010110017");
    }

    @Test
    public void shouldReturnHelperCorrectLength() {
        assertTrue(helper.isCorrectLength("900101-0017"));
    }

    @Test
    public void shouldRejectHelperIncorrectLengthToShort() {
        assertFalse(helper.isCorrectLength("00101-0017"));
    }

    @Test
    public void shouldRejectHelperIncorrectLengthToLong() {
        assertFalse(helper.isCorrectLength("9090101-00171"));
    }
    
    @Test 
    public void shouldReturnHelperCorrectDay() {
        assertTrue(helper.isValidDay("1"));
        assertTrue(helper.isValidDay("31"));
        assertTrue(helper.isValidDay("15"));
    }

    @Test
    public void shouldRejectHelperIncorrectDay() {
        assertFalse(helper.isValidDay("-1"));
        assertFalse(helper.isValidDay("0"));
        assertFalse(helper.isValidDay("32"));
    }

    @Test
    public void shouldReturnHelperCorrectMonth() {
        assertTrue(helper.isValidMonth("1"));
        assertTrue(helper.isValidMonth("6"));
        assertTrue(helper.isValidMonth("12"));
    }

    @Test
    public void shouldRejectHelperIncorrectMonth() {
        assertFalse(helper.isValidMonth("-1"));
        assertFalse(helper.isValidMonth("0"));
        assertFalse(helper.isValidMonth("13"));
    }

    @Test
    public void shouldReturnHelperCorrectFormat() {
        assertTrue(helper.isCorrectFormat("900101-0017"));
    }

    @Test
    public void shouldRejectHelperIncorrectFormat() {
        assertFalse(helper.isCorrectFormat("9001010017"));
    }

    @Test
    public void shouldReturnHelperIsCorrectLuhn() {
        assertTrue(helper.luhnIsCorrect("900101-0017"));
    }

    @Test
    public void shouldRejectHelperIsIncorrectLuhn() {
        assertFalse(helper.luhnIsCorrect("900101 0017"));
    }
}