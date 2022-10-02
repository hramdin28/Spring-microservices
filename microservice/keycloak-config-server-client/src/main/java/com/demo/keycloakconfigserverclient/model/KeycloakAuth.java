package com.demo.keycloakconfigserverclient.model;

public record KeycloakAuth(String clientIdKey,
                           String clientIdValue,
                           String clientSecretKey,
                           String clientSecretValue,
                           String grantTypeKey,
                           String grantTypeValue) {
}
