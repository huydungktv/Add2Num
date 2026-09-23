package com.example.dto;

import jakarta.validation.constraints.NotBlank;

public record AdditionRequest(String firstNumber, String secondNumber) {
	@Override
	@NotBlank(message = "firstNumber is required")
	public String firstNumber() {
		return firstNumber;
	}

	@Override
	@NotBlank(message = "secondNumber is required")
	public String secondNumber() {
		return secondNumber;
	}
}