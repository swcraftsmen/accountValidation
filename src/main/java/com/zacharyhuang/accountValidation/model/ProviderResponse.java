package com.zacharyhuang.accountValidation.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProviderResponse {
  private String name;
  private boolean isInvalid;
}
