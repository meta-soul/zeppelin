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

    protected final String authUrl;
    protected final String tokenUrl;

    public CasdoorApi(final String authUrl, final String tokenUrl){
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
}
