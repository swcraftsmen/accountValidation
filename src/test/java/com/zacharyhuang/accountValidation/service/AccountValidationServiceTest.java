package com.zacharyhuang.accountValidation.service;

import com.zacharyhuang.accountValidation.model.AccountValidationRequest;
import com.zacharyhuang.accountValidation.model.ProviderResponse;
import com.zacharyhuang.accountValidation.model.ServiceResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AccountValidationServiceTest {

  @Test
  void validate_success_case() {
    AccountValidationService accountValidationService =
        new AccountValidationServiceImpl((provider, accountNumber) ->
                CompletableFuture.completedFuture(new ProviderResponse("provider1", true))
        );
    ServiceResult result =
        accountValidationService.validate(
            new AccountValidationRequest("1234", Arrays.asList("provider1"))
        );

    assertTrue(result.isSuccess());
    ProviderResponse expectedProviderResponse = new ProviderResponse("provider1", true);
    assertEquals(1, result.getData().get().getResult().size());
    assertEquals(
        expectedProviderResponse.getName(), result.getData().get().getResult().get(0).getName());
    assertEquals(
        expectedProviderResponse.isInvalid(),
        result.getData().get().getResult().get(0).isInvalid());
  }
}
