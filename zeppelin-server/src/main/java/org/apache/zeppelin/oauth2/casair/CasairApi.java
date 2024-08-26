package org.apache.zeppelin.oauth2.casair;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasairApi
 * @Description TODO
 * @createTime 2024/1/31 6:00
 */
public class CasairApi extends DefaultApi20 {
    private static  final Logger LOGGER = LoggerFactory.getLogger(CasairApi.class);

    protected final String authUrl;
    protected final String tokenUrl;

    public CasairApi(final String authUrl, final String tokenUrl){
        this.authUrl = authUrl;
        this.tokenUrl = tokenUrl;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return tokenUrl;
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return authUrl;
    }

    @Override
    public OAuth20Service createService(String apiKey, String apiSecret, String callback, String defaultScope, String responseType, OutputStream debugStream, String userAgent, HttpClientConfig httpClientConfig, HttpClient httpClient) {
        return new CasairService(this,apiKey, apiSecret, callback, defaultScope, responseType, debugStream, userAgent, httpClientConfig, httpClient);
    }
}
