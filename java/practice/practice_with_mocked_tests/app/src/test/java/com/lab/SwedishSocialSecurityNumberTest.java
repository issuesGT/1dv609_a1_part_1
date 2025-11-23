package com.lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
    public void isNotCorrectLength() throws Exception {
        SSNHelper mock = mock(SSNHelper.class);

        when(mock.isCorrectLength("9001-0017")).thenReturn(false);

        Exception thrown = assertThrows(Exception.class,() -> new SwedishSocialSecurityNumber("9001-0017", mock));
        assertEquals("To short, must be 11 characters", thrown.getMessage());

        verify(mock).isCorrectLength("9001-0017");
    }

    @Test
    public void doesNotTrim() {
        SSNHelper mock = mock(SSNHelper.class);

        when(mock.isCorrectLength("900101-0017")).thenReturn(true);
        when(mock.isCorrectFormat("900101-0017")).thenReturn(true);
        when(mock.isValidDay("01")).thenReturn(true);
        when(mock.isValidMonth("01")).thenReturn(true);
        when(mock.luhnIsCorrect("900101-0017")).thenReturn(true);

        assertDoesNotThrow( () -> new SwedishSocialSecurityNumber(" 900101-0017 ", mock));

        
    }

    @Test
    public void isNotCorrectYear() {
        SSNHelper mock = mock(SSNHelper.class);

        when(mock.isCorrectLength("900101-0017")).thenReturn(true);
        when(mock.isCorrectFormat("900101-0017")).thenReturn(true);
        when(mock.isValidDay("01")).thenReturn(true);
        when(mock.isValidMonth("01")).thenReturn(true);
        when(mock.luhnIsCorrect("900101-0017")).thenReturn(true);

        var thrown = assertDoesNotThrow(() -> new SwedishSocialSecurityNumber("900101-0017", mock));

        assertEquals("90", thrown.getYear());
    }

    @Test
    public void isNotCorrectFormat() throws Exception {
        SSNHelper mock = mock(SSNHelper.class);
        
        when(mock.isCorrectLength("90010110017")).thenReturn(true);
        when(mock.isCorrectFormat("90010110017")).thenReturn(false);

        Exception thrown = assertThrows(Exception.class, () -> new SwedishSocialSecurityNumber("90010110017", mock));

        assertEquals("Incorrect format, must be: YYMMDD-XXXX", thrown.getMessage());

        verify(mock).isCorrectLength("90010110017");
        verify(mock).isCorrectFormat("90010110017");
    }

    @Test
    public void isNotCorrectDay() {
        SSNHelper mock = mock(SSNHelper.class);
        
        when(mock.isCorrectLength("900132-0017")).thenReturn(true);
        when(mock.isCorrectFormat("900132-0017")).thenReturn(true);
        when(mock.isValidDay("31")).thenReturn(true);
        when(mock.isValidMonth("01")).thenReturn(true);
        when(mock.luhnIsCorrect("900132-0017")).thenReturn(true);

        Exception thrown = assertThrows(Exception.class, () -> new SwedishSocialSecurityNumber("900132-0017", mock));

        assertEquals("Invalid day in SSN", thrown.getMessage());
    }

    @Test
    public void isNotCorrectMonth() {
        SSNHelper mock = mock(SSNHelper.class);
        
        when(mock.isCorrectLength("901301-0017")).thenReturn(true);
        when(mock.isCorrectFormat("901301-0017")).thenReturn(true);
        when(mock.isValidDay("01")).thenReturn(true);
        when(mock.isValidMonth("12")).thenReturn(true);
        when(mock.luhnIsCorrect("901301-0017")).thenReturn(true);

        Exception thrown = assertThrows(Exception.class, () -> new SwedishSocialSecurityNumber("901301-0017", mock));

        assertEquals("Invalid month in SSN", thrown.getMessage());
    }

    @Test
    public void isNotCorrectLuhn() throws Exception {
        SSNHelper mock = mock(SSNHelper.class);
        
        when(mock.isCorrectLength("90010110017")).thenReturn(true);
        when(mock.isCorrectFormat("90010110017")).thenReturn(true);
        when(mock.isValidDay("01")).thenReturn(true);
        when(mock.isValidMonth("01")).thenReturn(true);
        when(mock.luhnIsCorrect("900101-0017")).thenReturn(true);

        Exception thrown = assertThrows(Exception.class, () -> new SwedishSocialSecurityNumber("90010110017", mock));

        assertEquals("Invalid SSN according to Luhn's algorithm", thrown.getMessage());
    }

    @Test
    public void helperIsCorrectLength() {
        assertTrue(helper.isCorrectLength("900101-0017"));
    }

    @Test
    public void helperIsWrongLength() {
        assertFalse(helper.isCorrectLength("00101-0017"));
    }
    
    @Test
    public void helperIsCorrectDayFirstMiddelLast() {
        assertTrue(helper.isValidDay("1"));
        assertTrue(helper.isValidDay("31"));
        assertTrue(helper.isValidDay("15"));
    }

    @Test
    public void helperIsNotCorrectDay() {
        assertFalse(helper.isValidDay("-1"));
        assertFalse(helper.isValidDay("0"));
        assertFalse(helper.isValidDay("32"));
    }

    @Test
    public void helperIsCorrectMonthFirstMiddleLast() {
        assertTrue(helper.isValidMonth("1"));
        assertTrue(helper.isValidMonth("6"));
        assertTrue(helper.isValidMonth("12"));
    }

    @Test
    public void helperIsNotCorrectMonth() {
        assertFalse(helper.isValidMonth("-1"));
        assertFalse(helper.isValidMonth("0"));
        assertFalse(helper.isValidMonth("13"));
    }

    @Test
    public void helperIsCorrectFormat() {
        assertTrue(helper.isCorrectFormat("900101-0017"));
    }

    @Test
    public void helperIsNotCorrectFormat() {
        assertFalse(helper.isCorrectFormat("9001010017"));
    }

    @Test
    public void helperIsCorrectLuhn() {
        assertTrue(helper.luhnIsCorrect("900101-0017"));
    }

    @Test
    public void helperIsNotCorrectLuhn() {
        assertFalse(helper.luhnIsCorrect("900101 0017"));
    }
}