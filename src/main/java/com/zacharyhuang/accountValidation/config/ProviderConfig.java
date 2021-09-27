package com.zacharyhuang.accountValidation.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@AllArgsConstructor
@Setter
@Getter
@Component
@EnableConfigurationProperties
@ConfigurationProperties("external")
public class ProviderConfig {
    private HashMap<String, String> providers;
}
