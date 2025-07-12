package kh.edu.cstad.mbapi.service;

import kh.edu.cstad.mbapi.domain.Account;
import kh.edu.cstad.mbapi.dto.AccountResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountRequest;
import kh.edu.cstad.mbapi.dto.DisableAccountRequest;
import kh.edu.cstad.mbapi.dto.UpdateAccountRequest;

import java.util.List;

public interface AccountService {

    AccountResponse createNewAccount(CreateAccountRequest createAccountRequest);

    List<AccountResponse> findAll();

    AccountResponse findByAccountNo(String accountNo);

    void deleteByAccountNo(String accountNo);

    AccountResponse updateByAccountNo(String accountNo, UpdateAccountRequest updateAccountRequest);

    AccountResponse findByCustomer(String phoneNumber);

    AccountResponse disableAccountByAccountNo(String accountNo, DisableAccountRequest disableAccountRequest);

}
