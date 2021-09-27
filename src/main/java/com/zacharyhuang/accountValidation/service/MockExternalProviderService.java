package com.zacharyhuang.accountValidation.service;

import com.zacharyhuang.accountValidation.config.ProviderConfig;
import com.zacharyhuang.accountValidation.model.ProviderResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
public class MockExternalProviderService implements ExternalProviderService {
    private HashMap<String, String> providers;

    @Autowired
    public MockExternalProviderService(ProviderConfig providerConfig){
        this.providers = providerConfig.getProviders();
    }

    @Override
    @Async
    public CompletableFuture<ProviderResponse> requestValidate(String provider, String accountNumber) {
        /**
         * In reality, we should create a http client with max waiting to perform the request. Since the requirement
         * explicitly mention that the maximum response from all external provide is 1 seconds.
         *
         * Note, I don't use @Async annotation because I want to return specific data when error future fail unexpectlly
         */

        try {
            log.info(String.format("Send the request to %s", this.providers.get(provider)));
            Thread.sleep(1000);
            return CompletableFuture.completedFuture(new ProviderResponse(provider, true));
        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(new ProviderResponse(provider, true));
        }

    }
}
