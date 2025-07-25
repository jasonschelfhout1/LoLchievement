package com.lolchievement.config;

import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Deprecated
public class OAuthRestTemplate extends RestTemplate {
    private static final String CLIENT_REGISTRATION_ID = "riot";
    private static final String PRINCIPAL_NAME = "lolchievement-client";

    /**
     * @deprecated
     */
    public OAuthRestTemplate(final AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager) {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory();
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE); // keep {} intact for URI variables
        this.setUriTemplateHandler(uriBuilderFactory);

        this.getInterceptors().add((request, body, execution) -> {
            OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                    .withClientRegistrationId(CLIENT_REGISTRATION_ID)
                    .principal(PRINCIPAL_NAME)
                    .build();

            OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);

            if (authorizedClient == null || authorizedClient.getAccessToken() == null) {
                throw new IllegalStateException("Cannot obtain access token for client: " + CLIENT_REGISTRATION_ID);
            }

            String token = authorizedClient.getAccessToken().getTokenValue();

            request.getHeaders().setBearerAuth(token);

            return execution.execute(request, body);
        });
    }
}
