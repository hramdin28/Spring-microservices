package com.demo.keycloakconfigserverclient.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("custom.keycloak")
public class KeycloakCustom {
    private String uri;
    private String tokenUri;
    private String tokenIntrospect;
    private KeycloakAuth auth;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTokenUri() {
        return tokenUri;
    }

    public void setTokenUri(String tokenUri) {
        this.tokenUri = tokenUri;
    }

    public String getTokenIntrospect() {
        return tokenIntrospect;
    }

    public void setTokenIntrospect(String tokenIntrospect) {
        this.tokenIntrospect = tokenIntrospect;
    }

    public KeycloakAuth getAuth() {
        return auth;
    }

    public void setAuth(KeycloakAuth auth) {
        this.auth = auth;
    }
}
