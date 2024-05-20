package org.apache.zeppelin.oauth2.csair.prod;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.oauth.AccessTokenRequestParams;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasairService
 * @Description TODO
 * @createTime 2024/1/31 23:40
 */
public class CsairService extends OAuth20Service {
    private static  final Logger LOGGER = LoggerFactory.getLogger(CsairService.class);
    public CsairService(DefaultApi20 api, String apiKey, String apiSecret, String callback, String defaultScope, String responseType, OutputStream debugStream, String userAgent, HttpClientConfig httpClientConfig, HttpClient httpClient) {
        super(api, apiKey, apiSecret, callback, defaultScope, responseType, debugStream, userAgent, httpClientConfig, httpClient);
    }

    @Override
    protected OAuthRequest createAccessTokenRequest(AccessTokenRequestParams params) {
        final OAuthRequest request = new OAuthRequest(getApi().getAccessTokenVerb(), getApi().getAccessTokenEndpoint());
        request.addQuerystringParameter(OAuthConstants.CODE, params.getCode());
        request.addQuerystringParameter(OAuthConstants.GRANT_TYPE, OAuthConstants.AUTHORIZATION_CODE);
        request.addQuerystringParameter(OAuthConstants.CLIENT_ID, getApiKey());
        request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, getApiSecret());
        LOGGER.debug("GET AccessToken Url is {}", request.getCompleteUrl());
        LOGGER.debug("Get AccessToken params : {}", request.getQueryStringParams().getParams().toString());
        return request;
    }

    @Override
    protected OAuth2AccessToken sendAccessTokenRequestSync(OAuthRequest request)
            throws IOException, InterruptedException, ExecutionException {
        try (Response response = execute(request)) {
            LOGGER.debug("SendAccessTokenRequest: response status code: {}", response.getCode());
            final String body = response.getBody();
            LOGGER.debug("SendAccessTokenRequest: response body: {}", body);

            OAuth2AccessToken token = getApi().getAccessTokenExtractor().extract(response);
            LOGGER.debug("CasairService extracted token: {}", token.getAccessToken());
            return token;
        }
    }
}
