package com.example.gebruiker.stravaapitest;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.oauth.OAuth20Service;

/**
 * Strava's OAuth2 client's implementation
 * source: https://developers.strava.com/docs/authentication/
 */

public class StravaOAuth2Api extends DefaultApi20 {

    @SuppressWarnings("WeakerAccess")
    protected StravaOAuth2Api() {}

    private static class InstanceHolder {
        private static final StravaOAuth2Api INSTANCE = new StravaOAuth2Api();
    }

    @SuppressWarnings("WeakerAccess")
    public static StravaOAuth2Api instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://www.strava.com/oauth/authorize";
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://www.strava.com/oauth/token";
    }

    @Override
    public String getRevokeTokenEndpoint() {
        return "https://www.strava.com/oauth/deauthorize";
    }

    @Override
    public OAuth20Service createService(OAuthConfig config) {
        return new StravaOAuth20ServiceImpl(this, config);
    }
}