package kh.edu.cstad.mbapi.dto;

public record AccountResponse(
        String accountNo,
        String balance,
        String overLimit,
        Boolean isDeleted,
        String accountType
) {
}
