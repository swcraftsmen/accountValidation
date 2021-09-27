package com.zacharyhuang.accountValidation.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zacharyhuang.accountValidation.model.AccountNumberValidateResultResponse;
import com.zacharyhuang.accountValidation.model.AccountValidationRequest;
import com.zacharyhuang.accountValidation.model.ProviderResponse;
import com.zacharyhuang.accountValidation.service.AccountValidationService;
import com.zacharyhuang.accountValidation.service.AccountValidationServiceImpl;
import com.zacharyhuang.accountValidation.service.ExternalProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  private ExternalProviderService externalProviderService =
      (provider, accountNumber) ->
          CompletableFuture.completedFuture(new ProviderResponse(provider, true));

  AccountValidationService accountValidationService =
      new AccountValidationServiceImpl(externalProviderService);

  @Test
  void test_with_valid_request_body() throws Exception {
    AccountValidationRequest requestBody =
        new AccountValidationRequest("122", Arrays.asList("provider1"));

    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/v1/account/validate")
                    .content(objectMapper.writeValueAsString(requestBody))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    String responseBody = result.getResponse().getContentAsString();
    AccountNumberValidateResultResponse validationResponse =
        objectMapper.readValue(responseBody, AccountNumberValidateResultResponse.class);

    assertTrue(
        validationResponse.getAccountNumber().equalsIgnoreCase(requestBody.getAccountNumber()));
    assertTrue(validationResponse.getResult().get(0).isInvalid());
  }

  @Test
  void test_with_invalid_provider_name() throws Exception {
    AccountValidationRequest requestBody =
        new AccountValidationRequest("122", Arrays.asList("provider10"));

    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/v1/account/validate")
                    .content(objectMapper.writeValueAsString(requestBody))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String responseBody = result.getResponse().getContentAsString();
    assertTrue(responseBody.compareToIgnoreCase("{\"error\":\"invalid_request\"}") == 0);
  }

  @Test
  void test_with_empty_request_body() throws Exception {

    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/v1/account/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String responseBody = result.getResponse().getContentAsString();
    assertTrue(responseBody.compareToIgnoreCase("{\"error\":\"invalid_request\"}") == 0);
  }

  @Test
  void test_with_missing_required_field() throws Exception {
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/v1/account/validate")
                    .content("{\"accountNumber\":\"122\"}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String responseBody = result.getResponse().getContentAsString();
    assertTrue(responseBody.compareToIgnoreCase("{\"error\":\"invalid_request\"}") == 0);
  }
}
