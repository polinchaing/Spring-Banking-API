package kh.edu.cstad.mbapi.dto;

import java.time.LocalDate;

public record CreateCustomerRequest(
        String fullName,
        String gender,
        String email,
        String phoneNumber,
        String remark,
        String nationalIdCard,
        String dob,
        String customerSegment
) {
}
