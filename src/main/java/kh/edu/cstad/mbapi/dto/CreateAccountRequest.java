package kh.edu.cstad.mbapi.dto;

import java.math.BigDecimal;


public record CreateAccountRequest(
        String phoneNumber,
        BigDecimal balance,
        BigDecimal overLimit
) {
}
