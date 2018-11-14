package com.example.ibanking;

import com.example.ibanking.domain.CaptureResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("user1")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-data-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-data-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MappingTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    private CaptureResponse response = mock(CaptureResponse.class);

    @Test
    public void profilePageTest() throws Exception {
        this.mockMvc.perform(get("/user/profile"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id=\"navbarSupportedContent\"]/div/a").string("user1"));
    }

    @Test
    public void accountsPageTest() throws Exception {
        this.mockMvc.perform(get("/accounts"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id=\"accountList\"]/div").nodeCount(2));
    }

    @Test
    public void branchesPageTest() throws Exception {
        this.mockMvc.perform(get("/branches").param("cityFilter", "Moscow"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id=\"branchList\"]/div").nodeCount(2))
                .andExpect(xpath("//*[@id=\"1\"]").exists())
                .andExpect(xpath("//*[@id=\"2\"]").exists());
    }

    @Test
    public void userRegistrationTest() throws Exception {
        Mockito.doReturn(response)
                .when(restTemplate)
                .postForObject(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.any()
                );

        Mockito.doReturn(true)
                .when(response)
                .isSuccess();

        this.mockMvc.perform(post("/registration")
                .param("username", "user3")
                .param("password", "333")
                .param("password2", "333")
                .param("userEmail", "jcoder3@gmail.com")
                .param("firstName", "Rogozhin")
                .param("lastName", "Viktor")
                .param("age", "65")
                .param("g-recaptcha-response", "")
                .with(csrf()))
                .andDo(print());

        this.mockMvc.perform(formLogin().user("user3").password("333"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}