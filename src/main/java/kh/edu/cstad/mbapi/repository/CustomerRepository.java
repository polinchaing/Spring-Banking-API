package kh.edu.cstad.mbapi.repository;

import kh.edu.cstad.mbapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    // SELECT * FROM CUSTOMERS WHERE PHONE NUMBER = ?
    Optional<Customer>findByPhoneNumber(String phoneNumber);

}

