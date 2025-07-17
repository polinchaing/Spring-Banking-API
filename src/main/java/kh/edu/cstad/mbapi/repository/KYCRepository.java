package kh.edu.cstad.mbapi.repository;

import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KYCRepository extends JpaRepository<KYC, String> {

    Boolean existsByNationalIdCard(String nationalIdCard);

    Optional<KYC> findByNationalIdCard(String nationalIdCard);

}
