package com.nhnacademy.minidoorayaccount.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nhnacademy.minidoorayaccount.dto.request.AccountStatusDto;
import com.nhnacademy.minidoorayaccount.dto.response.DefaultDto;
import com.nhnacademy.minidoorayaccount.entity.Account;
import com.nhnacademy.minidoorayaccount.entity.enums.UserStatus;
import com.nhnacademy.minidoorayaccount.exception.AccountNotFoundException;
import com.nhnacademy.minidoorayaccount.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = AccountController.class)
public class AccountControllerTest {
    private final Account mockAccountActive = new Account("user", "123", "example@naver.com", UserStatus.REGISTERED);
    private final String ACCOUNTS_PATH = "/accounts";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }

    @Test
    void getAccountByUserId() throws Exception {
        when(accountService.getAccount(mockAccountActive.getUserId())).thenReturn(mockAccountActive);

        MvcResult result = mockMvc.perform(get(ACCOUNTS_PATH + "/" + mockAccountActive.getUserId())
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .header("accountId", 1L))
                .andExpect(status().isOk())
                .andReturn();
        DefaultDto<Account> expected = new DefaultDto<>(200, mockAccountActive);
        DefaultDto<Account> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        assertEquals(expected, actual);
    }

    @Test
    void getAccountByUserId_notFound() throws Exception {
        when(accountService.getAccount(mockAccountActive.getUserId())).thenThrow(AccountNotFoundException.class);
        mockMvc.perform(get(ACCOUNTS_PATH + "/" + mockAccountActive.getUserId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("accountId", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAccount() throws Exception {
        when(accountService.getAccount(anyLong())).thenReturn(mockAccountActive);
        MvcResult result = mockMvc.perform(get(ACCOUNTS_PATH)
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .header("accountId", 1L)
                )
                .andExpect(status().isOk())
                .andReturn();
        DefaultDto<Account> expected = new DefaultDto<>(200, mockAccountActive);
        DefaultDto<Account> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        assertEquals(expected, actual);
    }

    @Test
    void getAccount_notFound() throws Exception {
        when(accountService.getAccount(anyLong())).thenThrow(AccountNotFoundException.class);
        mockMvc.perform(get(ACCOUNTS_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("accountId", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateAccountStatus() throws Exception {
        when(accountService.updateStatus(anyLong(), any(AccountStatusDto.class))).thenReturn(mockAccountActive);
        MvcResult result = mockMvc.perform(patch(ACCOUNTS_PATH)
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .header("accountId", 1L)
                                                   .content(objectMapper.writeValueAsString(mockAccountActive)))
                .andExpect(status().isOk())
                .andReturn();
        DefaultDto<Account> expected = new DefaultDto<>(200, mockAccountActive);
        DefaultDto<Account> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        assertEquals(expected, actual);
    }

    @Test
    void updateAccountStatus_notFound() throws Exception {
        when(accountService.updateStatus(anyLong(), any(AccountStatusDto.class))).thenThrow(AccountNotFoundException.class);
        mockMvc.perform(patch(ACCOUNTS_PATH)
                                .header("accountId", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockAccountActive)))
                .andExpect(status().isNotFound());
    }
}
