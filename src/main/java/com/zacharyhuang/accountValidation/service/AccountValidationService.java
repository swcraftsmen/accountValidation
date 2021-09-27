package com.zacharyhuang.accountValidation.service;

import com.zacharyhuang.accountValidation.model.AccountValidationRequest;
import com.zacharyhuang.accountValidation.model.ServiceResult;

public interface AccountValidationService {
  /**
   * An aggregator method that invoke the external provider service to send the request to provider.
   * The method consolidate all response into a single response.
   * @param requestBody [[AccountValidationRequest]]
   * @return [[ServiceResult]] either successful result or error
   */
  ServiceResult validate(AccountValidationRequest requestBody);
}
