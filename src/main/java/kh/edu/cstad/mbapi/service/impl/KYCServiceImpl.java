package kh.edu.cstad.mbapi.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kh.edu.cstad.mbapi.domain.KYC;
import kh.edu.cstad.mbapi.repository.KYCRepository;
import kh.edu.cstad.mbapi.service.KYCService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KYCServiceImpl implements KYCService {

    private final KYCRepository kycRepository;

    @Override
    public void verifyByNationalIdCard(String nationalIdCard) {
        KYC kyc = kycRepository
                .findByNationalIdCard(nationalIdCard)
                .orElseThrow(()->new EntityNotFoundException("NationalCardId not found"));

        kyc.setIsVerified(true);
        kycRepository.save(kyc);
    }

}
