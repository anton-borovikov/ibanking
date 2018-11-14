package com.example.ibanking.repo;

import com.example.ibanking.domain.Account;
import com.example.ibanking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepo extends JpaRepository<Account, Long> {
    List<Account> findByUser(User user);
    Account findByAccountNumber(String accountNumber);
}
