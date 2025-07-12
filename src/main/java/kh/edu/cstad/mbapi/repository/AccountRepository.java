package kh.edu.cstad.mbapi.repository;

import kh.edu.cstad.mbapi.domain.Account;
import kh.edu.cstad.mbapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Boolean existsByAccountNo(String accountNo);

    Optional<Account> findByAccountNo(String accountNo);

    void deleteByAccountNo(String accountNo);

    Optional<Account> findByCustomer(Customer customer);

}
