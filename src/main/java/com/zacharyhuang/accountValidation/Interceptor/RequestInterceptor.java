package com.zacharyhuang.accountValidation.Interceptor;

import com.zacharyhuang.accountValidation.util.CorrelationID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

@Component
public class RequestInterceptor implements HandlerInterceptor {
  private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    long startTime = Instant.now().toEpochMilli();
    request.setAttribute("startTime", startTime);

    final String userAgent = request.getHeader("User-Agent");
    String correlationId = request.getHeader(CorrelationID.HEADER_NAME);
    if (correlationId == null || correlationId.isEmpty()) {
      correlationId = CorrelationID.generate();
    }
    CorrelationID.set(correlationId);
    MDC.put(CorrelationID.HEADER_NAME, correlationId);
    logger.info(
        String.format(
            "Request: %s %s %s %s",
            request.getMethod(), request.getRequestURI(), request.getProtocol(), userAgent));
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    long startTime = (Long) request.getAttribute("startTime");

    response.setHeader(CorrelationID.HEADER_NAME, CorrelationID.getOrGenerate());
    CorrelationID.clear();
    logger.info(
        String.format(
            "Response: status=%s durationMs=%s",
            response.getStatus(), (Instant.now().toEpochMilli() - startTime)));
  }
}
