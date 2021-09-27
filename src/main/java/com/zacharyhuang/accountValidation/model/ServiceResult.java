package com.zacharyhuang.accountValidation.model;

import lombok.Data;

import java.util.Optional;

@Data
public final class ServiceResult {
  private boolean success;
  private Optional<ServiceError> error;
  private Optional<AccountNumberValidateResultResponse> data;

  private ServiceResult(
      boolean success,
      Optional<ServiceError> error,
      Optional<AccountNumberValidateResultResponse> data) {
    this.success = success;
    this.error = error;
    this.data = data;
  }

  public static ServiceResult success(AccountNumberValidateResultResponse data) {
    return new ServiceResult(true, Optional.empty(), Optional.of(data));
  }

  public static ServiceResult fail(ServiceError error) {
    return new ServiceResult(true, Optional.of(error), Optional.empty());
  }
}
