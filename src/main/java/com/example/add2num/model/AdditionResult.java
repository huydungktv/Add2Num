package com.example.add2num.model;

import java.util.List;

public record AdditionResult(
        String firstNumber,
        String secondNumber,
        String result,
        List<AdditionStep> steps) {

    public record AdditionStep(
            int position,
            int firstDigit,
            int secondDigit,
            int carryIn,
            int total,
            int resultDigit,
            int carryOut) {
    }
}
