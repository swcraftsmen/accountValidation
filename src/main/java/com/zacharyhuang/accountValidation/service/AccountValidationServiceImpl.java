package com.zacharyhuang.accountValidation.service;

import com.zacharyhuang.accountValidation.model.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AccountValidationServiceImpl implements AccountValidationService {

  private final ExternalProviderService externalProviderService;

  @Autowired
  public AccountValidationServiceImpl(ExternalProviderService externalProviderService) {
    this.externalProviderService = externalProviderService;
  }

  public ServiceResult validate(AccountValidationRequest requestBody) {
    List<CompletableFuture<ProviderResponse>> futureProvider =
        requestBody.getProviders().stream()
            .map(
                provider ->
                    externalProviderService.requestValidate(
                        provider, requestBody.getAccountNumber()))
            .collect(Collectors.toList());

    CompletableFuture<List<ProviderResponse>> allResponse =
        CompletableFuture.allOf(
                futureProvider.toArray(new CompletableFuture[futureProvider.size()]))
            .thenApply(
                future ->
                    futureProvider.stream()
                        .map(completableFuture -> completableFuture.join())
                        .collect(Collectors.toList()));
    try {
      AccountNumberValidateResultResponse allValidationResponse =
          new AccountNumberValidateResultResponse(
              requestBody.getAccountNumber(), allResponse.get());
      return ServiceResult.success(allValidationResponse);
    } catch (Exception e) {
      log.warn("Unable to validate the account number. error=" + e.getLocalizedMessage());
      return ServiceResult.fail(new ServiceError(ErrorCode.InternalServerError));
    }
  }
}
