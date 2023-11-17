package com.ScootersApp.unitTests;

import com.ScootersApp.Service.DTOs.account.response.AccountResponseDTO;
import com.ScootersApp.controller.AccountController;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class AccountTest {
    AccountController accountController;

    @Test
    public void checkCorrectWalletAmount(){
        List<AccountResponseDTO> accounts = accountController.getAllAccounts();
        for (AccountResponseDTO account : accounts) {
                Double expected = 0.0;
                Double obtained = account.getWallet();
                Assert.assertTrue(obtained>=expected);
        }
    }
}
