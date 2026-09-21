package com.example.add2num.service;

import com.example.add2num.model.AdditionRequest;
import com.example.add2num.model.AdditionResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdditionServiceTest {

    private final AdditionService additionService = new AdditionService();

    @Test
    void shouldUseBigNumberLibraryAndExposeCalculationSteps() {
        AdditionResult result = additionService.add(new AdditionRequest("1234", "897"));

        assertEquals("2131", result.result());
        assertEquals(4, result.steps().size());
        assertEquals(1, result.steps().getFirst().carryOut());
        assertEquals(2, result.steps().getLast().resultDigit());
    }

    @Test
    void shouldRejectBlankInput() {
        assertThrows(IllegalArgumentException.class,
                () -> additionService.add(new AdditionRequest("", "1")));
    }

    @Test
    void shouldSupportVeryLargeNumbers() {
        AdditionResult result = additionService.add(new AdditionRequest(
                "999999999999999999999999999999", "1"));

        assertEquals("1000000000000000000000000000000", result.result());
        assertFalse(result.steps().isEmpty());
    }
}
