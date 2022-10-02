package com.demo.keycloakconfigserverclient.config;


import com.demo.keycloakconfigserverclient.exception.KeycloakCustomException;
import com.demo.keycloakconfigserverclient.model.KeycloakCustom;
import com.demo.keycloakconfigserverclient.model.KeycloakTokenResponse;
import org.apache.http.Header;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


public class CustomConfigServiceBootstrapConfiguration {
    private final KeycloakCustom keycloakCustom;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomConfigServiceBootstrapConfiguration.class);
    private static final String HEADER = "Authorization";
    private static final String SPACE = " ";
    private static final String ERROR_NO_KEYCLOAK_RESPONSE = "Error: No response found";

    public CustomConfigServiceBootstrapConfiguration(KeycloakCustom keycloakCustom) {
        this.keycloakCustom = keycloakCustom;
    }

    @ConditionalOnProperty(
            value = "custom.keycloak.enabled",
            havingValue = "true")
    @Bean
    public ConfigServicePropertySourceLocator configServicePropertySourceLocator(
            ConfigClientProperties configClientProperties) throws KeycloakCustomException {

        LOGGER.info("Loading CustomConfigServiceBootstrapConfiguration bean");

        var configServicePropertySourceLocator = new ConfigServicePropertySourceLocator(configClientProperties);
        configServicePropertySourceLocator.setRestTemplate(customRestTemplate());
        return configServicePropertySourceLocator;
    }

    private List<Header> headers(KeycloakTokenResponse response) {
        var basicHeader = new BasicHeader(HEADER, response.token_type() + SPACE + response.access_token());
        return List.of(basicHeader);
    }

    private RestTemplate customRestTemplate() throws KeycloakCustomException {
        var keycloakResponse = getAuthTokenFromKeycloak();
        var httpClient = HttpClients.custom()
                .setDefaultHeaders(headers(keycloakResponse))
                .build();

        var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(requestFactory);
    }

    private HttpEntity<MultiValueMap<String, String>> buildRequest() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var mapForm = new LinkedMultiValueMap<String, String>();
        mapForm.add(keycloakCustom.getAuth().clientIdKey(), keycloakCustom.getAuth().clientIdValue());
        mapForm.add(keycloakCustom.getAuth().clientSecretKey(), keycloakCustom.getAuth().clientSecretValue());
        mapForm.add(keycloakCustom.getAuth().grantTypeKey(), keycloakCustom.getAuth().grantTypeValue());

        return new HttpEntity<>(mapForm, headers);
    }

    private KeycloakTokenResponse getAuthTokenFromKeycloak() throws KeycloakCustomException {
        var restTemplate = new RestTemplate();

        var request = buildRequest();

        try {
            var respo = restTemplate.exchange(
                    keycloakCustom.getTokenUri(),
                    HttpMethod.POST, request, KeycloakTokenResponse.class);
            return Optional.ofNullable(respo.getBody())
                    .orElseThrow(() -> new KeycloakCustomException(ERROR_NO_KEYCLOAK_RESPONSE));

        } catch (RestClientResponseException error) {
            throw new KeycloakCustomException(error.getLocalizedMessage());
        }
    }

}
