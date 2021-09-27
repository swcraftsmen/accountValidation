package com.zacharyhuang.accountValidation.Interceptor;

import com.zacharyhuang.accountValidation.model.ErrorCode;
import com.zacharyhuang.accountValidation.model.ServiceError;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
      log.warn(ex.getLocalizedMessage());
    return ResponseEntity.status(status).body(new ServiceError(ErrorCode.InvalidRequest));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    logger.warn(ex.getLocalizedMessage());
    return ResponseEntity.status(status).body(new ServiceError(ErrorCode.InvalidRequest));
  }
}
