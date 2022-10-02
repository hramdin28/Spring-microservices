package com.demo.keycloakconfigserverclient.model;

public record KeycloakTokenResponse(String access_token, String token_type, String scope) {
}
