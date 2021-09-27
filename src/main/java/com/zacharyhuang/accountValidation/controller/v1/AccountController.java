package com.zacharyhuang.accountValidation.controller.v1;

import com.zacharyhuang.accountValidation.config.ProviderConfig;
import com.zacharyhuang.accountValidation.model.AccountValidationRequest;
import com.zacharyhuang.accountValidation.model.ErrorCode;
import com.zacharyhuang.accountValidation.model.ServiceError;
import com.zacharyhuang.accountValidation.model.ServiceResult;
import com.zacharyhuang.accountValidation.service.AccountValidationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

  private final AccountValidationService accountValidationService;

  private final HashMap<String, String> providers;

  @Autowired
  public AccountController(
      AccountValidationService accountValidationService, ProviderConfig providerConfig) {
    this.accountValidationService = accountValidationService;
    this.providers = providerConfig.getProviders();
  }

  @PostMapping(value = "/validate", consumes = "application/json", produces = "application/json")
  public ResponseEntity validate(
      @Valid @RequestBody AccountValidationRequest requestBody) {

    List<String> validProvider =
        requestBody.getProviders().stream()
            .filter(p -> providers.containsKey(p))
            .collect(Collectors.toList());
    if (validProvider.size() != requestBody.getProviders().size()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new ServiceError(ErrorCode.InvalidRequest));
    }
    ServiceResult result = this.accountValidationService.validate(requestBody);
    if (result.isSuccess()) {
      return ResponseEntity.status(HttpStatus.OK).body(result.getData().get());
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getError().get());
    }
  }
}
