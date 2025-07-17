package kh.edu.cstad.mbapi.dto;

import kh.edu.cstad.mbapi.util.CurrencyUtil;

import java.math.BigDecimal;


public record CreateAccountRequest(
        String phoneNumber,
        BigDecimal balance,
        CurrencyUtil accCurrency,
        String accountType,
        String accName,
        String accNo
) {
}
