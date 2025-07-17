package kh.edu.cstad.mbapi.repository;

import kh.edu.cstad.mbapi.domain.Account;
import kh.edu.cstad.mbapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Boolean existsByAccNo(String accNo);

    Optional<Account> findByAccNo(String accNo);

    void deleteByAccNo(String accNo);

    Optional<Account> findByCustomer(Customer customer);

}
