package com.example.controller;

import com.example.dto.AdditionRequest;
import com.example.dto.AdditionResponse;
import com.example.service.AdditionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/additions")
@Tag(name = "Addition", description = "Operations on arbitrarily large non-negative integers")
public class AdditionController {

    private final AdditionService additionService;

    public AdditionController(AdditionService additionService) {
        this.additionService = additionService;
    }

    @PostMapping
    @Operation(summary = "Add two large numbers")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Numbers added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = com.example.dto.ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid API key")
    })
    public ResponseEntity<AdditionResponse> add(@Valid @RequestBody AdditionRequest request) {
        String result = additionService.add(request.firstNumber(), request.secondNumber());
        return ResponseEntity.ok(new AdditionResponse(result));
    }

    @PostMapping("/add")
    public ResponseEntity<AdditionResponse> add(@RequestParam String firstNumber, @RequestParam String secondNumber) {
        String result = additionService.add(firstNumber, secondNumber);
        return ResponseEntity.ok(new AdditionResponse(result));
    }
}