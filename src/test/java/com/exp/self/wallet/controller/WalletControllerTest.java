package com.exp.self.wallet.controller;

import com.exp.self.wallet.dto.OperationType;
import com.exp.self.wallet.dto.WalletRequest;
import com.exp.self.wallet.entity.Wallet;
import com.exp.self.wallet.exception.GlobalExceptionHandler;
import com.exp.self.wallet.exception.WalletNotFoundException;
import com.exp.self.wallet.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UUID testWalletId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(walletController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void deposit_ShouldReturnOK() throws Exception {
        UUID testWalletId = UUID.randomUUID();
        WalletRequest request = new WalletRequest(testWalletId, OperationType.DEPOSIT, BigDecimal.valueOf(1000));

        Wallet mockWallet = new Wallet(testWalletId, BigDecimal.valueOf(1000));

        when(walletService.deposit(any(UUID.class), any(BigDecimal.class))).thenReturn(mockWallet);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(testWalletId.toString()))
                .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    void getBalance_ShouldReturnNotFound() throws Exception {
        when(walletService.getBalance(testWalletId))
                .thenThrow(new WalletNotFoundException(testWalletId));

        mockMvc.perform(get("/api/v1/wallet/" + testWalletId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }
}