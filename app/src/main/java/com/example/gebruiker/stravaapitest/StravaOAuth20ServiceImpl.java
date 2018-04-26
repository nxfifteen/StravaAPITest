package com.example.gebruiker.stravaapitest;

/**
 * Created by Gebruiker on 20/04/2018.
 */

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.revoke.TokenTypeHint;

/**
 * Strava's OAuth2 client's implementation
 * source: https://developers.strava.com/docs/authentication/
 */

public class StravaOAuth20ServiceImpl extends OAuth20Service {

    StravaOAuth20ServiceImpl(DefaultApi20 api, OAuthConfig config) {
        super(api, config);
    }

    @Override
    protected OAuthRequest createAccessTokenRequest(String code) {
        //System.out.println(getApi().getAccessTokenEndpoint());

        OAuthRequest request = new OAuthRequest(Verb.POST, getApi().getAccessTokenEndpoint());
        OAuthConfig config = this.getConfig();
        request.addParameter("client_id", config.getApiKey());
        request.addParameter("client_secret", config.getApiSecret());
        request.addParameter("code", code);
        request.addParameter("redirect_uri", config.getCallback());

        return request;
    }

    @Override
    protected OAuthRequest createRevokeTokenRequest(String tokenToRevoke, TokenTypeHint tokenTypeHint) {
        OAuthRequest request = new OAuthRequest(Verb.POST, getApi().getRevokeTokenEndpoint());
        request.addParameter("token",tokenToRevoke);

        return request;
    }
}

