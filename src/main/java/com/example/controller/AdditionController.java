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

    /**
     * Constructs the addition controller with the service used to perform large-number addition.
     *
     * @param additionService the service that handles addition logic
     */
    public AdditionController(AdditionService additionService) {
        this.additionService = additionService;
    }

    /**
     * Adds two large non-negative integers submitted as a JSON request body.
     *
     * @param request the request containing the two numbers to add
     * @return a response entity containing the sum as a string
     */
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

    /**
     * Adds two large non-negative integers submitted as request parameters.
     *
     * @param firstNumber the first number to add
     * @param secondNumber the second number to add
     * @return a response entity containing the sum as a string
     */
    @PostMapping("/add")
    public ResponseEntity<AdditionResponse> add(@RequestParam String firstNumber, @RequestParam String secondNumber) {
        String result = additionService.add(firstNumber, secondNumber);
        return ResponseEntity.ok(new AdditionResponse(result));
    }
}