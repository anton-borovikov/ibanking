package com.example.ibanking;

import com.example.ibanking.controller.MainController;
import com.example.ibanking.controller.RegistrationController;
import com.example.ibanking.controller.UserController;
import com.example.ibanking.domain.CaptureResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IbankingApplicationTests {
	@Autowired
	private MainController mainController;
	@Autowired
	private RegistrationController registrationController;
	@Autowired
	private UserController userController;
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RestTemplate restTemplate;
	private CaptureResponse response = mock(CaptureResponse.class);

	@Test
	public void contextLoads() {
	}

	@Test
	public void controllersTest() throws Exception {
		assertThat(mainController).isNotNull();
		assertThat(registrationController).isNotNull();
		assertThat(userController).isNotNull();
	}

	@Test
	public void startPageTest() throws Exception {
		this.mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Welcome to iBanking service application!")));
	}

	@Test
	public void accountPageAccessDeniedTest() throws Exception {
		this.mockMvc.perform(get("/accounts"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	public void loginPageAcceptTest() throws Exception {
		this.mockMvc.perform(formLogin().user("user1").password("111"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
	}

	@Test
	public void loginPagePostAcceptTest() throws Exception {
		this.mockMvc.perform(post("/login")
					.param("username", "user1")
					.param("password", "111")
					.with(csrf()))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
	}

	@Test
	public void loginPagePostDeniedTest() throws Exception {
		this.mockMvc.perform(post("/login")
				.param("username", "user2")
				.param("password", "111")
				.with(csrf()))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login?error"));
	}
}
