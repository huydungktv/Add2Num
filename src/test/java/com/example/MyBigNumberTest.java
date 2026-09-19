package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyBigNumberTest {

    private final MyBigNumber myBigNumber = new MyBigNumber();

    @Test
    void shouldAddTwoNumbersWithSameLength() {
        assertEquals("579", myBigNumber.sum("123", "456"));
    }

    @Test
    void shouldAddNumbersWithDifferentLengths() {
        assertEquals("2131", myBigNumber.sum("1234", "897"));
    }

    @Test
    void shouldHandleCarryAcrossAllDigits() {
        assertEquals("1000", myBigNumber.sum("999", "1"));
    }

    @Test
    void shouldHandleFinalCarry() {
        assertEquals("100", myBigNumber.sum("99", "1"));
    }

    @Test
    void shouldAddZero() {
        assertEquals("12345", myBigNumber.sum("12345", "0"));
    }

    @Test
    void shouldHandleVeryLargeNumbers() {
        assertEquals("1000000000000000000000000000000",
                myBigNumber.sum("999999999999999999999999999999", "1"));
    }

    @Test
    void shouldRejectNullInput() {
        assertThrows(IllegalArgumentException.class, () -> myBigNumber.sum(null, "1"));
    }

    @Test
    void shouldRejectNonDigitInput() {
        assertThrows(IllegalArgumentException.class, () -> myBigNumber.sum("12a", "3"));
    }
}
