package com.example.gebruiker.stravaapitest;

import android.content.Context;
import android.net.Uri;
import android.os.StrictMode;
import android.support.customtabs.CustomTabsIntent;
import android.util.Log;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Gebruiker on 26/04/2018.
 */

public class NxStravaHelper {

    @SuppressWarnings("FieldCanBeLocal")
    private String clientid = "xxx";
    @SuppressWarnings("FieldCanBeLocal")
    private String clientSecret = "xxxxx";
    @SuppressWarnings("FieldCanBeLocal")
    private String apiCallback = "http://localhost";
    @SuppressWarnings("FieldCanBeLocal")
    private String apiScope = "view_private,write";
    private String response_type = "code";
    private String TAG = "tralala";
    private String authCode;
    private OAuth20Service service;
    private OAuth2AccessToken accessToken;

    //Constructor, allows for all kinds of network access, application will not block. This is ok since this flow is only run once as long as the scope stays the same.
    NxStravaHelper(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    //Points the application to Chrome Custom Tabs to get the Code
    static void sendUserToAuthorisation(Context callingContext) {
        NxStravaHelper helperClass = new NxStravaHelper();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
        CustomTabsIntent customTabsIntent = builder.build();
        // and launch the desired Url with CustomTabsIntent.launchUrl()
        customTabsIntent.launchUrl(callingContext, Uri.parse(helperClass.getAuthorizationUrl()));
    }

    //Method creates the OAuth20Service if none is already running
    private String getAuthorizationUrl() {
        return getService().getAuthorizationUrl();
    }

    //works with OAuth20Service, gathers all parameters to make the call
    private OAuth20Service createService() {
        return new ServiceBuilder(clientid)
                .apiSecret(clientSecret)
                .responseType(response_type)
                .scope(apiScope)
                .callback(apiCallback)  //your callback URL to store and handle the authorization code sent by Fitbit
                .build(StravaOAuth2Api.instance());
    }

    //Creates service if necessary
    private OAuth20Service getService() {
        if (service == null) {
            service = createService();
        }
        return service;
    }

    //You extract the Code from the "code" variable in the returned Uri
    private void setAuthCodeFromIntent(Uri returnUrl) {
        authCode = returnUrl.getQueryParameter("code");
        Log.d(TAG, "Auth Code set to " + authCode);
    }

    //You get the Token with the Access Code
    private void requestAccessToken() {
        try {
            OAuth20Service serviceCall = getService();
            accessToken = serviceCall.getAccessToken(authCode);
            Log.d(TAG, "Access Token=" + accessToken);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    //Get access Code and get Token with Code
    void requestAccessTokenFromIntent(Uri returnUrl) {
        setAuthCodeFromIntent(returnUrl);
        requestAccessToken();
    }
}
