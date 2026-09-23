package com.example;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Arrays;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@State(Scope.Benchmark)
public class MyBigNumberBenchmark {

    @Param({"10", "1000", "100000"})
    private int numberOfDigits;

    private MyBigNumber bigNumber;
    private String firstNumber;
    private String secondNumber;

    @Setup(Level.Trial)
    public void setUp() {
        bigNumber = new MyBigNumber();
        firstNumber = repeatedDigit('9', numberOfDigits);
        secondNumber = repeatedDigit('1', numberOfDigits);
    }

    @Benchmark
    public void sum(Blackhole blackhole) {
        blackhole.consume(bigNumber.sum(firstNumber, secondNumber));
    }

    private String repeatedDigit(char digit, int length) {
        char[] digits = new char[length];
        Arrays.fill(digits, digit);
        return new String(digits);
    }
}