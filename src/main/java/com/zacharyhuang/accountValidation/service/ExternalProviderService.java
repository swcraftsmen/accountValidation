package com.zacharyhuang.accountValidation.service;

import com.zacharyhuang.accountValidation.model.ProviderResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Base of External Provider which define the common attributes and functionality. For this
 * take-home assessment, this maybe be redundant because we are only simulating the request instead
 * of actually making the request. In reality, each of provider has their own requirements such as
 * headers or api keys.
 */
@Service
public interface ExternalProviderService {

  /**
   * This is a "do everything" method. It does 1. Build the http request 2. Send the request 3.
   * Handle response for the happy path 4. Handle unexpected error such as connection timeout.
   *
   * <p>P.S Since this method does asynchronously, the method catch all errors within the method and
   * simply return and CompletableFuture<ProviderResponse>. The consumer side (who calls the method)
   * can expected that all futures returned are in the completed state.
   *
   * @param provider The account number validation provider
   * @param accountNumber Account number
   * @return Completed future of ProviderResponse but the isValid is either true or false based upon
   *     the provider response as well as whether the error occurred.
   */
  CompletableFuture<ProviderResponse> requestValidate(
          String provider, String accountNumber);
}
