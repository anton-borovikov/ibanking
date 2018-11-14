package com.example.ibanking;

import com.example.ibanking.domain.User;
import com.example.ibanking.repo.RoleRepo;
import com.example.ibanking.repo.UserRepo;
import com.example.ibanking.service.MailSender;
import com.example.ibanking.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;
    @MockBean
    private RoleRepo roleRepo;
    @MockBean
    private MailSender mailSender;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void addUserTest() {
        User user = new User();

        assertTrue(userService.addUser(user));
        assertNotNull(user.getActivationCode());
        assertTrue(user.getRoles().contains(roleRepo.findByName("user")));

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(mailSender, Mockito.times(1)).sendMail(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        );
    }

    @Test
    public void addUserFailedTest() {
        User user = new User();

        user.setUsername("Vadim");

//        Mockito.doReturn(new User())
//                .when(userRepo)
//                .findByUsername("Vadim");

        Mockito.when(userRepo.findByUsername("Vadim"))
                .thenReturn(new User());

        assertFalse(userService.addUser(user));

        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
        Mockito.verify(mailSender, Mockito.times(0)).sendMail(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        );
    }

    @Test
    public void activateUserTest() {
        User user = new User();

        Mockito.when(userRepo.findByActivationCode("Code"))
                .thenReturn(user);

        assertTrue(userService.activateUser("Code"));
        assertNull(user.getActivationCode());
        Mockito.verify(userRepo, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
    }
}
