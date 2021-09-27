package com.zacharyhuang.accountValidation.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountNumberValidateResultResponse {
  private String accountNumber;
  private List<ProviderResponse> result;
}
