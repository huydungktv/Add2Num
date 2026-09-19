package com.example.add2num.controller;

import com.example.add2num.model.AdditionRequest;
import com.example.add2num.model.AdditionResult;
import com.example.add2num.service.AdditionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdditionController {

    private final AdditionService additionService;

    public AdditionController(AdditionService additionService) {
        this.additionService = additionService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/add")
    public String add(@RequestParam String firstNumber,
                      @RequestParam String secondNumber,
                      Model model) {
        AdditionResult result = additionService.add(
                new AdditionRequest(firstNumber, secondNumber));
        model.addAttribute("addition", result);
        return "index";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleInvalidInput(IllegalArgumentException exception, Model model) {
        model.addAttribute("error", exception.getMessage());
        return "index";
    }
}
