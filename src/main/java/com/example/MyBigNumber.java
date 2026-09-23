package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBigNumber {

    private static final Logger log = LoggerFactory.getLogger(MyBigNumber.class);

    public String sum(String firstNumber, String secondNumber) {
        validateInput(firstNumber);
        validateInput(secondNumber);

        StringBuilder reversedResult = new StringBuilder();
        int index1 = firstNumber.length() - 1;
        int index2 = secondNumber.length() - 1;
        int carry = 0;
        int digit1;
        int digit2;
        int total;
        int resultDigit;

        while (index1 >= 0 || index2 >= 0 || carry > 0) {
            digit1 = index1 >= 0 ? firstNumber.charAt(index1) - '0' : 0;
            digit2 = index2 >= 0 ? secondNumber.charAt(index2) - '0' : 0;
            total = digit1 + digit2 + carry;
            resultDigit = total % 10;
            carry = total / 10;

            reversedResult.append(resultDigit);
                log.debug("digit1={}, digit2={}, resultDigit={}, carry={}",
                    digit1, digit2, resultDigit, carry);

            index1--;
            index2--;
        }

        return reversedResult.reverse().toString();
    }

    private void validateInput(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Input must contain at least one digit");
        }

        int index = 0;
        while (index < value.length()) {
            if (value.charAt(index) < '0' || value.charAt(index) > '9') {
                throw new IllegalArgumentException("Input must contain digits only");
            }
            index++;
        }
    }
}
