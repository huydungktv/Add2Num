package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBigNumber {

    private static final Logger logger = LoggerFactory.getLogger(MyBigNumber.class);

    public String sum(String stn1, String stn2) {
        validateInput(stn1);
        validateInput(stn2);

        StringBuilder reversedResult = new StringBuilder();
        int index1 = stn1.length() - 1;
        int index2 = stn2.length() - 1;
        int carry = 0;

        while (index1 >= 0 || index2 >= 0 || carry > 0) {
            int digit1 = index1 >= 0 ? stn1.charAt(index1) - '0' : 0;
            int digit2 = index2 >= 0 ? stn2.charAt(index2) - '0' : 0;
            int total = digit1 + digit2 + carry;
            int resultDigit = total % 10;
            carry = total / 10;

            reversedResult.append(resultDigit);
            logger.info("digit1={}, digit2={}, resultDigit={}, carry={}",
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

        for (int index = 0; index < value.length(); index++) {
            if (value.charAt(index) < '0' || value.charAt(index) > '9') {
                throw new IllegalArgumentException("Input must contain digits only");
            }
        }
    }
}
