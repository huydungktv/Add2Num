package com.example.add2num.service;

import com.example.MyBigNumber;
import com.example.add2num.model.AdditionRequest;
import com.example.add2num.model.AdditionResult;
import com.example.add2num.model.AdditionResult.AdditionStep;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdditionService {

    private final MyBigNumber bigNumber = new MyBigNumber();

    public AdditionResult add(AdditionRequest request) {
        String firstNumber = normalize(request.firstNumber());
        String secondNumber = normalize(request.secondNumber());
        String result = bigNumber.sum(firstNumber, secondNumber);

        return new AdditionResult(firstNumber, secondNumber, result,
                buildSteps(firstNumber, secondNumber));
    }

    private List<AdditionStep> buildSteps(String firstNumber, String secondNumber) {
        List<AdditionStep> steps = new ArrayList<>();
        int firstIndex = firstNumber.length() - 1;
        int secondIndex = secondNumber.length() - 1;
        int carry = 0;
        int position = 1;

        while (firstIndex >= 0 || secondIndex >= 0 || carry > 0) {
            int firstDigit = firstIndex >= 0 ? firstNumber.charAt(firstIndex) - '0' : 0;
            int secondDigit = secondIndex >= 0 ? secondNumber.charAt(secondIndex) - '0' : 0;
            int carryIn = carry;
            int total = firstDigit + secondDigit + carryIn;
            int resultDigit = total % 10;
            carry = total / 10;

            steps.add(new AdditionStep(position++, firstDigit, secondDigit,
                    carryIn, total, resultDigit, carry));
            firstIndex--;
            secondIndex--;
        }

        return steps;
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Please enter both numbers.");
        }
        return value.trim();
    }
}
