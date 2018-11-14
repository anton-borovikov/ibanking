package com.example.ibanking.service;

import com.example.ibanking.domain.Account;
import com.example.ibanking.domain.Role;
import com.example.ibanking.domain.User;
import com.example.ibanking.repo.AccountRepo;
import com.example.ibanking.repo.RoleRepo;
import com.example.ibanking.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private AccountRepo accountRepo;
    private MailSender mailSender;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, RoleRepo roleRepo, MailSender mailSender, AccountRepo accountRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.mailSender = mailSender;
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean addUser(User user) {
        User userDB = userRepo.findByUsername(user.getUsername());
        if (userDB != null) {
            return false;
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepo.findByName("user"));
        user.setRoles(roles);

        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        String message = String.format(
                "Hello, %s! \n\n" +
                        "Wellcome to iBanking.\n \n" +
                        "Please click next link to activate your iBanking account:\n" +
                        "http://localhost:8080/activate/%s",
                user.getFirstName() + " " + user.getLastName(),
                user.getActivationCode()
        );
        mailSender.sendMail(user.getUserEmail(), "Wellcome to iBanking!", message);

        return true;
    }

    public void updateUserProfile(User user, String userEmail, String firstName, String lastName, Integer age) {
        if (userEmail != null && !user.getUserEmail().equals(userEmail)) {
            user.setUserEmail(userEmail);
        }
        if (firstName != null && !user.getFirstName().equals(firstName)) {
            user.setFirstName(firstName);
        }
        if (lastName != null && !user.getLastName().equals(lastName)) {
            user.setLastName(lastName);
        }
        if ((age != null) && !user.getAge().equals(age)) {
            user.setAge(age);
        }
        userRepo.save(user);
    }

    public void reSendActivationCode(User user) {
        User userDB = userRepo.findByUsername(user.getUsername());

        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);

        String message = String.format(
                "Hello, %s!\n\n" +
                        "You have requested a new code to activate your iBanking account.\n\n" +
                        "Please click next link to activate it:\n" +
                        "http://localhost:8080/activate/%s",
                user.getFirstName() + " " + user.getLastName(),
                user.getActivationCode()
        );

        mailSender.sendMail(user.getUserEmail(), "iBanking: Activation code", message);
    }

    public boolean transfer(String accountFromStr, String accountToStr, Double amount) {
        Account accountFrom = accountRepo.findByAccountNumber(accountFromStr);
        Account accountTo = accountRepo.findByAccountNumber(accountToStr);

        accountFrom.setAccountBalance(accountFrom.getAccountBalance() - amount);
        accountTo.setAccountBalance(accountTo.getAccountBalance() + amount);

        accountRepo.save(accountFrom);
        accountRepo.save(accountTo);

        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        user.setActivated(true);
        userRepo.save(user);

        return true;
    }
}
