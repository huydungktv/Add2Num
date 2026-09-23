package com.example.dto;

public record ApiError(String type, String title, int status, String detail) {
}