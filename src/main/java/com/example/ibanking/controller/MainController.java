package com.example.ibanking.controller;

import com.example.ibanking.domain.*;
import com.example.ibanking.repo.AccountRepo;
import com.example.ibanking.repo.BranchRepo;
import com.example.ibanking.repo.CityRepo;
import com.example.ibanking.repo.ServiceRepo;
import com.example.ibanking.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {
    private ServiceRepo serviceRepo;
    private BranchRepo branchRepo;
    private CityRepo cityRepo;
    private AccountRepo accountRepo;
    private UserService userService;

    public MainController(ServiceRepo serviceRepo, BranchRepo branchRepo, CityRepo cityRepo, AccountRepo accountRepo, UserService userService) {
        this.serviceRepo = serviceRepo;
        this.branchRepo = branchRepo;
        this.cityRepo = cityRepo;
        this.accountRepo = accountRepo;
        this.userService = userService;
    }

    @GetMapping("/")
    public String startPage(@AuthenticationPrincipal User user,
                            Model model) {
        model.addAttribute("user", user);
        model.addAttribute("helloText", "Welcome to iBanking service application!");

        return "start";
    }

    @GetMapping("/services")
    public String servicePage(@AuthenticationPrincipal User user,
                              Model model) {
        model.addAttribute("user", user);
        List<Service> serviceList = serviceRepo.findAll();
        model.addAttribute("services", serviceList);
        return "service";
    }

    @GetMapping("/accounts")
    public String accountPage(@AuthenticationPrincipal User user,
                              Model model) {
        model.addAttribute("user", user);
        List<Account> accountList = accountRepo.findByUser(user);
        model.addAttribute("accountsUser", accountList);
        accountList = accountRepo.findAll();
        model.addAttribute("accountsAll", accountList);
        return "accounts";
    }

    @PostMapping("/accounts")
    public String transferPayment(@AuthenticationPrincipal User user,
                                  @RequestParam("accountFrom") String accountFromMix,
                                  @RequestParam("accountTo") String accountToMix,
                                  @RequestParam("amount") Double amount,
                                  Model model) {
        model.addAttribute("user", user);
        List<Account> accountList = accountRepo.findByUser(user);
        model.addAttribute("accountsUser", accountList);
        accountList = accountRepo.findAll();
        model.addAttribute("accountsAll", accountList);

        String accountFrom = accountFromMix.split(" ")[0];
        String accountTo = accountToMix.split(" ")[0];

        if (accountFrom.equals(accountTo)) {
            model.addAttribute("message", "Account must be different!");
            model.addAttribute("message_type", "warning");
            return "accounts";
        }

        if (amount <= 0) {
            model.addAttribute("message", "Payment amount must be greater than zero!");
            model.addAttribute("message_type", "warning");
            return "accounts";
        }

        Double balanceFrom = Double.parseDouble(accountFromMix.split(" ")[2].replaceAll("\\W", ""));

        if (balanceFrom < amount) {
            model.addAttribute("message", "Payment amount exceeds the amount on the account!");
            model.addAttribute("message_type", "warning");
            return "accounts";
        }

        if (userService.transfer(accountFrom, accountTo, amount)) {
            model.addAttribute("message", "Payment was successful!");
            model.addAttribute("message_type", "success");
        } else {
            model.addAttribute("message", "Payment failed!");
            model.addAttribute("message_type", "warning");
        }

        return "accounts";
    }

    @GetMapping("/branches")
    public String branchePage(@RequestParam(name = "cityFilter", defaultValue = "All") String cityFilter,
                              @AuthenticationPrincipal User user,
                              Model model) {
        model.addAttribute("user", user);

        List<City> cityList = cityRepo.findAll();
        model.addAttribute("cities", cityList);

        if (cityFilter.equals("All")) {
            List<Branch> branchList = branchRepo.findAll();
            model.addAttribute("branches", branchList);
        } else {
            City city = cityRepo.findByCityName(cityFilter);
            List<Branch> branchList = branchRepo.findByCity(city);
            model.addAttribute("branches", branchList);
        }

        return "branch";
    }
}
