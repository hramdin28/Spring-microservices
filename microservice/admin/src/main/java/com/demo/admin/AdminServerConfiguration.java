package com.demo.admin;

import com.demo.keycloakconfigserverclient.config.CustomConfigServiceBootstrapConfiguration;
import com.demo.keycloakconfigserverclient.exception.KeycloakCustomException;
import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class AdminServerConfiguration {
    @Bean
    public HttpHeadersProvider customHttpHeadersProvider(
            CustomConfigServiceBootstrapConfiguration customConfigServiceBootstrapConfiguration) throws KeycloakCustomException {

        var keycloakResponse = customConfigServiceBootstrapConfiguration.getAuthTokenFromKeycloak();

        return (instance) -> {
            var httpHeaders = new HttpHeaders();
            httpHeaders.add(
                    CustomConfigServiceBootstrapConfiguration.HEADER,
                    keycloakResponse.token_type() + CustomConfigServiceBootstrapConfiguration.SPACE + keycloakResponse.access_token());
            return httpHeaders;
        };
    }
}
