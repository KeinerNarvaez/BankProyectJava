package com.Bank.BankBackend.modules.gmf.application.useCase;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculateGmfUseCaseTest {

    private final CalculateGmfUseCase calculateGmfUseCase = new CalculateGmfUseCase();

    @Test
    void shouldReturnZeroWhenAccountIsGmfExempt() {

        BigDecimal amount =
                new BigDecimal("100000");

        BigDecimal result =
                calculateGmfUseCase.calculate(
                        true,
                        amount
                );

        assertEquals(
                0,
                BigDecimal.ZERO.compareTo(result)
        );
    }

    @Test
    void shouldCalculateGmfSuccessfully() {

        BigDecimal amount =
                new BigDecimal("100000");

        BigDecimal result =
                calculateGmfUseCase.calculate(
                        false,
                        amount
                );

        assertEquals(
                0,
                new BigDecimal("400")
                        .compareTo(result)
        );
    }

    @Test
    void shouldCalculateGmfWhenExemptionIsNull() {

        BigDecimal amount =
                new BigDecimal("50000");

        BigDecimal result =
                calculateGmfUseCase.calculate(
                        null,
                        amount
                );

        assertEquals(
                0,
                new BigDecimal("200")
                        .compareTo(result)
        );
    }

    @Test
    void shouldReturnAvailableBalanceWhenAccountIsGmfExempt() {

        BigDecimal availableBalance =
                new BigDecimal("100000");

        BigDecimal result =
                calculateGmfUseCase.calculateMaxWithdrawAmount(
                        true,
                        availableBalance
                );

        assertEquals(
                0,
                availableBalance.compareTo(result)
        );
    }

    @Test
    void shouldCalculateMaxWithdrawAmountSuccessfully() {

        BigDecimal availableBalance =
                new BigDecimal("100000");

        BigDecimal result =
                calculateGmfUseCase.calculateMaxWithdrawAmount(
                        false,
                        availableBalance
                );

        assertEquals(
                0,
                new BigDecimal("99601.59")
                        .compareTo(result)
        );
    }

    @Test
    void shouldCalculateMaxWithdrawAmountWhenExemptionIsNull() {

        BigDecimal availableBalance =
                new BigDecimal("50000");

        BigDecimal result =
                calculateGmfUseCase.calculateMaxWithdrawAmount(
                        null,
                        availableBalance
                );

        assertEquals(
                0,
                new BigDecimal("49800.79")
                        .compareTo(result)
        );
    }
}