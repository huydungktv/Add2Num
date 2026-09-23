package com.example.service;

import com.example.MyBigNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdditionService {

    private final MyBigNumber bigNumber;
    private final int maxNumberLength;

    public AdditionService(
            @Value("${addition.max-number-length:100000}") int maxNumberLength) {
        this.bigNumber = new MyBigNumber();
        this.maxNumberLength = maxNumberLength;
    }

    public String add(String firstNumber, String secondNumber) {
        validateLength(firstNumber);
        validateLength(secondNumber);
        return bigNumber.sum(firstNumber, secondNumber);
    }

    private void validateLength(String number) {
        if (number != null && number.length() > maxNumberLength) {
            throw new IllegalArgumentException("Input number exceeds the maximum allowed length");
        }
    }
}