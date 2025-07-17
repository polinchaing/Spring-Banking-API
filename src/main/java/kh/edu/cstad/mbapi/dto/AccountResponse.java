package kh.edu.cstad.mbapi.dto;

public record AccountResponse(
        String accNo,
        String balance,
        Boolean isHide,
        String accountType,
        String accName,
        String accCurrency
) {
}
