package com.example.client.proxy;

import com.example.client.managers.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ResourceServerProxy {
  private static final Logger LOG = LoggerFactory.getLogger(ResourceServerProxy.class);
  public static final String AUTHORIZATION = "Authorization";

  @Value("${resource.server.url}")
  private String resourceServerURL;

  private final TokenManager tokenManager;

  private final RestTemplate restTemplate;

  public ResourceServerProxy(TokenManager tokenManager, RestTemplate restTemplate) {
    this.tokenManager = tokenManager;
    this.restTemplate = restTemplate;
  }

  public String callDemo() {
    String token = tokenManager.getAccessToken();
    LOG.trace("Auth token=" + token);

    String url = resourceServerURL + "/demo";
    url = "https://fhir.epic.com/interconnect-fhir-oauth/api/FHIR/R4/CarePlan/eq6ULRTTIxq1U-ALQSFZEHhkuJQ5qJy2SqkpSOKFlES4W6CwP2MtV5wnic0yKaTBj3";

    HttpHeaders headers = new HttpHeaders();
    headers.add(AUTHORIZATION, "Bearer " + token);

    LOG.info(headers.toString());
    HttpEntity<Void> request = new HttpEntity<>(headers);

    var response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

    return response.getBody();
  }
}
