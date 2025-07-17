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
import kh.edu.cstad.mbapi.util.CurrencyUtil;
import kh.edu.cstad.mbapi.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

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

        Account account = new Account();
        Random random = new Random();

        //validation Customer Phone Number
        Customer customer = customerRepository
                .findByPhoneNumber(createAccountRequest.phoneNumber())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Phone number not found")
                );

       //validation Account Type
        AccountType accountype = accountTypeRepository
                .findByType(createAccountRequest.accountType())
                .orElseThrow(()->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Account type not found")
                        );

        //Account Currency Section
        switch(createAccountRequest.accCurrency()){
            case CurrencyUtil.USD-> {
                if(createAccountRequest.balance().compareTo(BigDecimal.valueOf(10)) < 0){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance must be greater than or equal to 10");
                }
                //set OverLimit Base On Customer Segment
                if(customer.getCustomerSegment().getSegment().equals("REGULAR")){
                    account.setOverLimit(BigDecimal.valueOf(5000));
                }
                else if(customer.getCustomerSegment().getSegment().equals("SILVER")){
                    account.setOverLimit(BigDecimal.valueOf(10000));
                }
                else {
                    account.setOverLimit(BigDecimal.valueOf(50000));
                }
            }

            case CurrencyUtil.KHR -> {
                if(createAccountRequest.balance().compareTo(BigDecimal.valueOf(40000)) < 0){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance must be greater than or equal to 40000");
                }
                //set OverLimit Base On Customer Segment
                if(customer.getCustomerSegment().getSegment().equals("REGULAR")){
                    account.setOverLimit(BigDecimal.valueOf(5000 * 4100));
                }
                else if(customer.getCustomerSegment().getSegment().equals("SILVER")){
                    account.setOverLimit(BigDecimal.valueOf(10000 * 4000));
                }
                else {
                    account.setOverLimit(BigDecimal.valueOf(50000 * 4000));
                }
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid currency");
        }

        //validation Account No
        if(createAccountRequest.accNo() != null){
            if(accountRepository.existsByAccNo(createAccountRequest.accNo())){
                throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Account no %s already exists",createAccountRequest.accNo()));
            }
            account.setAccNo(createAccountRequest.accNo());
        }else{
            String actNo;
            do {
                actNo = String.format("%09d", new Random().nextInt(1_000_000_000)); // Max: 999,999,999
            } while (accountRepository.existsByAccNo(actNo));
            account.setAccNo(actNo);
        }

        //set Data Logic
        account.setAccName(createAccountRequest.accName());
        account.setAccCurrency(createAccountRequest.accCurrency().name());
        account.setBalance(createAccountRequest.balance());
        account.setIsHide(false);
        account.setIsDeleted(false);
        account.setCustomer(customer);
        account.setAccountType(accountype);

        account = accountRepository.save(account);

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
        return accountRepository.findByAccNo(accountNo)
                .map(accountMapper::toAccountResponse)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Number Not Found")
                );
    }

    @Transactional
    @Override
    public void deleteByAccountNo(String accountNo) {
     accountRepository.deleteByAccNo(accountNo);
    }


    @Override
    public AccountResponse updateByAccountNo(String accountNo, UpdateAccountRequest updateAccountRequest) {

        Account account = accountRepository
                .findByAccNo(accountNo)
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
                .findByAccNo(accountNo)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Number Not Found")
                );

        account.setIsDeleted(disableAccountRequest.isDeleted());

        accountRepository.save(account);

        return accountMapper.toAccountResponse(account);
    }


}
