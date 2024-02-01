package org.apache.zeppelin.oauth2.casair;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.apache.zeppelin.oauth2.casdoor.CasdoorApi;
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
    public static final String AUTHORIZE_ENDPOINT_URL = "https://deviam.csair.com/idp/oauth2/authorize";

    public static final String TOKEN_ENDPOINT_URL = "https://deviam.csair.com/idp/oauth2/getToken";

    private static class InstanceHolder {
        private static final CasairApi INSTANCE = new CasairApi();
    }

    public static CasairApi instance(){
        return CasairApi.InstanceHolder.INSTANCE;
    }
    @Override
    public String getAccessTokenEndpoint() {
        return TOKEN_ENDPOINT_URL;
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return AUTHORIZE_ENDPOINT_URL;
    }

    @Override
    public OAuth20Service createService(String apiKey, String apiSecret, String callback, String defaultScope, String responseType, OutputStream debugStream, String userAgent, HttpClientConfig httpClientConfig, HttpClient httpClient) {

        return new CasairService(this,apiKey, apiSecret, callback, defaultScope, responseType, debugStream, userAgent, httpClientConfig, httpClient);
    }
}
