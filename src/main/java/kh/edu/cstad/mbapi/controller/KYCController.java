package kh.edu.cstad.mbapi.controller;

import kh.edu.cstad.mbapi.service.KYCService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class KYCController {

    private final KYCService kycService;

    @PutMapping("/customers/{nationalIdCard}/verify")
    ResponseEntity<String> verify(@PathVariable String nationalIdCard) {
        kycService.verifyByNationalIdCard(nationalIdCard);
        return ResponseEntity.ok().body("Customer has been verified successfully");
    }
}
