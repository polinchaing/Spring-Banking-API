package kh.edu.cstad.mbapi.service.impl;

import kh.edu.cstad.mbapi.domain.Account;
import kh.edu.cstad.mbapi.domain.AccountType;
import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.dto.AccountResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountRequest;
import kh.edu.cstad.mbapi.dto.DisableAccountRequest;
import kh.edu.cstad.mbapi.dto.UpdateAccountRequest;
import kh.edu.cstad.mbapi.mapper.AccountMapper;
import kh.edu.cstad.mbapi.repository.AccountRepository;
import kh.edu.cstad.mbapi.repository.AccountTypeRepository;
import kh.edu.cstad.mbapi.repository.CustomerRepository;
import kh.edu.cstad.mbapi.repository.KYCRepository;
import kh.edu.cstad.mbapi.service.AccountService;
import kh.edu.cstad.mbapi.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final CustomerRepository customerRepository;

    private final AccountMapper accountMapper;

    private final Util util;

    private final AccountTypeRepository accountTypeRepository;

    private final KYCRepository kycRepository;

    @Override
    public AccountResponse createNewAccount(CreateAccountRequest createAccountRequest) {

        Customer customer = customerRepository
                .findByPhoneNumber(createAccountRequest.phoneNumber())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Phone number not found")
                );

        if (customer.getKyc().getIsVerified().equals(false)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        AccountType accountType = accountTypeRepository.findAccountTypeByType(createAccountRequest.accountType()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Type not found")
        );

        Account account = accountMapper.fromCreateAccountRequest(createAccountRequest);
        account.setIsDeleted(false);
        account.setCustomer(customer);
        account.setAccountNo(util.generateRandomAccountNo());
        account.setAccountType(accountType);
        account.setOverLimit(customer.getCustomerSegment().getOverLimit());
        account.setBalance(BigDecimal.ZERO);

        accountRepository.save(account);

        return accountMapper.toAccountResponse(account);

    }

    @Override
    public List<AccountResponse> findAll() {
            List<Account> accounts = accountRepository.findAll();
            return accounts
                    .stream()
                    .map(accountMapper::toAccountResponse)
                    .toList();
    }

    @Override
    public AccountResponse findByAccountNo(String accountNo) {
        return accountRepository.findByAccountNo(accountNo)
                .map(accountMapper::toAccountResponse)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Number Not Found")
                );
    }

    @Transactional
    @Override
    public void deleteByAccountNo(String accountNo) {
     accountRepository.deleteByAccountNo(accountNo);
    }


    @Override
    public AccountResponse updateByAccountNo(String accountNo, UpdateAccountRequest updateAccountRequest) {

        Account account = accountRepository
                .findByAccountNo(accountNo)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Number Not Found")
                );

        accountMapper.toAccountPartially
                (updateAccountRequest,
                        account);

        accountRepository.save(account);

        return accountMapper.toAccountResponse(account);

    }

    @Override
    public AccountResponse findByCustomer(String phoneNumber) {

        Customer customer = customerRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")
                );

        return accountMapper.toAccountResponse(
                accountRepository.findByCustomer(customer)
                        .orElseThrow(
                                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found")
                        ));
    }

    @Transactional
    @Override
    public AccountResponse disableAccountByAccountNo(String accountNo, DisableAccountRequest disableAccountRequest) {
        Account account = accountRepository
                .findByAccountNo(accountNo)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Number Not Found")
                );

        account.setIsDeleted(disableAccountRequest.isDeleted());

        accountRepository.save(account);

        return accountMapper.toAccountResponse(account);
    }


}
