package com.zacharyhuang.accountValidation.util;

import java.security.SecureRandom;

/**
 * The util class that generate the correlation id (track id) and store the correlation id in the ThreadLocal
 *
 */
public class CorrelationID {
    public static final String HEADER_NAME = "X-Correlation-ID";

    private static final ThreadLocal<String> correlationStore = new ThreadLocal<>();

    /**
     * Set the value of Correlation within the scope of current thread
     *
     * @param correlationId Correlation ID
     */
    public static void set(String correlationId) {
        correlationStore.set(correlationId);
    }

    /**
     * Retrieve Correlation ID from the thread store or generate a new Correlation ID
     *
     * @return Correlation Id
     */
    public static synchronized String getOrGenerate() {
        String correlationId = correlationStore.get();
        if (correlationId.isEmpty()) {
            correlationId = generate();
            correlationStore.set(correlationId);
        }
        return correlationId;
    }

    public static void clear() {
        correlationStore.remove();
    }

    public static String generate() {
        final String alphanumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final int length = alphanumeric.length();
        final SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder(24);
        for (int i = 0; i < 24; i++) {
            sb.append(alphanumeric.charAt(secureRandom.nextInt(length)));
        }
        return sb.toString();
    }
}
