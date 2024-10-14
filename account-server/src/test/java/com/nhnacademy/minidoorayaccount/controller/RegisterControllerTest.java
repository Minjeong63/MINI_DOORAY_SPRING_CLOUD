package com.nhnacademy.minidoorayaccount.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidoorayaccount.dto.request.AccountDto;
import com.nhnacademy.minidoorayaccount.dto.response.DefaultDto;
import com.nhnacademy.minidoorayaccount.entity.Account;
import com.nhnacademy.minidoorayaccount.entity.enums.UserStatus;
import com.nhnacademy.minidoorayaccount.exception.AccountAlreadyExistException;
import com.nhnacademy.minidoorayaccount.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = RegisterController.class)
class RegisterControllerTest {
    private final AccountDto testAccountDto = new AccountDto("user", "123", "ex@naver.com");
    private final Account mockAccountActive = new Account("user", "123", "example@naver.com", UserStatus.REGISTERED);
    private final String ACCOUNTS_PATH = "/accounts";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AccountService accountService;


    @Test
    void register() throws Exception {
        doNothing().when(accountService).saveAccount(testAccountDto);

        MvcResult result = mockMvc.perform(post("/register")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(objectMapper.writeValueAsString(testAccountDto)))
                .andExpect(status().isCreated())
                .andReturn();
        DefaultDto dto = new DefaultDto(201, null);
        assertEquals(dto, objectMapper.readValue(result.getResponse().getContentAsString(), DefaultDto.class));
    }

    @Test
    void register_alreadyExist() throws Exception {
        doThrow(AccountAlreadyExistException.class).when(accountService).saveAccount(testAccountDto);
        mockMvc.perform(post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testAccountDto)))
                .andExpect(status().isConflict());
    }
}
