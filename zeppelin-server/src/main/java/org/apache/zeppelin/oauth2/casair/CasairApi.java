package org.apache.zeppelin.oauth2.casair;

import com.github.scribejava.core.builder.api.DefaultApi20;
import org.apache.zeppelin.oauth2.casdoor.CasdoorApi;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasairApi
 * @Description TODO
 * @createTime 2024/1/31 6:00
 */
public class CasairApi extends DefaultApi20 {

    public static final String AUTHORIZE_ENDPOINT_URL = "https://deviam.casair.com/idp/oauth2/getToken";

    public static final String TOKEN_ENDPOINT_URL = "https://deviam.casair.com/idp/oauth2/getToken";

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
}
