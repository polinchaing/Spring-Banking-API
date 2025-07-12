package kh.edu.cstad.mbapi.controller;

import kh.edu.cstad.mbapi.domain.Account;
import kh.edu.cstad.mbapi.dto.AccountResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountRequest;
import kh.edu.cstad.mbapi.dto.DisableAccountRequest;
import kh.edu.cstad.mbapi.dto.UpdateAccountRequest;
import kh.edu.cstad.mbapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AccountResponse createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.createNewAccount(createAccountRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<AccountResponse> findAll() {
        return accountService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{accountNo}")
    public AccountResponse findByAccountNo(@PathVariable String accountNo) {
        return accountService.findByAccountNo(accountNo);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{accountNo}")
    public void deleteByAccountNo(@PathVariable String accountNo) {
        accountService.deleteByAccountNo(accountNo);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{accountNo}")
    public AccountResponse updateByAccountNo(
            @PathVariable String accountNo ,
            @RequestBody UpdateAccountRequest updateAccountRequest) {
        return accountService.updateByAccountNo(accountNo, updateAccountRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/customer/{phoneNumber}")
    public AccountResponse findByCustomer(@PathVariable String phoneNumber) {
        return accountService.findByCustomer(phoneNumber);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{accountNo}")
    public AccountResponse disableAccountByAccountNo(@PathVariable String accountNo, @RequestBody DisableAccountRequest disableAccountRequest) {
        return accountService.disableAccountByAccountNo(accountNo, disableAccountRequest);
    }
}
