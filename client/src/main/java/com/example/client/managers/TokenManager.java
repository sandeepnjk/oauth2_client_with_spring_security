package com.example.client.managers;

import com.example.client.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Component
public class TokenManager {
    private static final Logger LOG = LoggerFactory.getLogger(TokenManager.class);
    @Value("${client.registration.name}")
    private String clientRegistrationName;

    @Value("${spring.security.oauth2.client.registration.app.client-id}")
    private String clientId;

    @Autowired
    private JwtUtil jwtUtil;

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public TokenManager(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    public String getAccessToken() {
        String jwtToken =  "";
        try {
            jwtToken = jwtUtil.createToken();
            LOG.trace("jwtToken=" + jwtToken);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            //needs to be properly handled
            ex.printStackTrace();
        }
        OAuth2AuthorizeRequest authorizeRequest =

                OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistrationName)
                        .principal(clientId)
                        .attribute("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer")
                        .attribute("client_assertion",jwtToken)
                        .build();
        LOG.trace("authorizeRequest = " + authorizeRequest.toString());
        OAuth2AuthorizedClient authorizedClient =
                this.authorizedClientManager.authorize(authorizeRequest);

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();

        return accessToken.getTokenValue();
    }
}
