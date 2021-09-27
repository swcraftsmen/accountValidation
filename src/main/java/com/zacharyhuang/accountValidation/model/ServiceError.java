package com.zacharyhuang.accountValidation.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceError {
  private ErrorCode error;
}
