package kh.edu.cstad.mbapi.util;


import kh.edu.cstad.mbapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class Util {

    private final AccountRepository accountRepository;
    private final Random random = new Random();

    public String generateRandomAccountNo() {
        String accountNo;
        do {
            String num = "00" + String.format("%07d", random.nextInt(10_000_000));
            accountNo = num.replaceAll("(.{3})(?!$)", "$1_");
        } while (accountRepository.existsByAccNo(accountNo));
        return accountNo;
    }
}
