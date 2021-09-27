package com.zacharyhuang.accountValidation.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class AccountValidationRequest {
  @NotNull private String accountNumber;
  @NotNull private List<String> providers;
}
