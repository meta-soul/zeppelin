package org.apache.zeppelin.oauth2.casdoor;

import com.github.scribejava.core.builder.api.DefaultApi20;
import org.apache.zeppelin.realm.jwt.WorkspaceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasdoorApi
 * @Description TODO
 * @createTime 2024/1/31 3:27
 */
public class CasdoorApi extends DefaultApi20 {
    private static  final Logger LOGGER = LoggerFactory.getLogger(CasdoorApi.class);

    public static final String AUTHORIZE_ENDPOINT_URL = "http://172.23.132.36:19091/casdoor/login/oauth/authorize";

    public static final String TOKEN_ENDPOINT_URL = "http://172.23.132.36:8000/api/login/oauth/access_token";

    private static class InstanceHolder {
        private static final CasdoorApi INSTANCE = new CasdoorApi();
    }

    public static CasdoorApi instance(){
        return InstanceHolder.INSTANCE;
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
