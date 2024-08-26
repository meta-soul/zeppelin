package org.apache.zeppelin.oauth2.casdoor;

import org.pac4j.oauth.client.GenericOAuth20Client;
import org.pac4j.oauth.client.OAuth20Client;
import org.pac4j.oauth.profile.definition.OAuthProfileDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasdoorClient
 * @Description TODO
 * @createTime 2024/1/31 3:30
 */
public class CasdoorClient extends OAuth20Client {
    private static  final Logger LOGGER = LoggerFactory.getLogger(CasdoorClient.class);

    private String authUrl;
    private String tokenUrl;
    private String profileUrl;

    protected final String scope = "openid+profile+email";
    public CasdoorClient(){
        LOGGER.debug("Create CasdoorClient Success with no params");
        LOGGER.debug("Client Key is {}, Secret is {}", this.getKey(), this.getSecret());
    }

    public CasdoorClient(final String key, final String secret){
        LOGGER.debug("Create CasdoorClient Success");
        setKey(key);
        setSecret(secret);
    }

    @Override
    protected void clientInit() {
        LOGGER.debug("CasdoorClient init Start");

        configuration.setApi(new CasdoorApi(authUrl, tokenUrl));
        configuration.setWithState(false);
        CasdoorProfileDefinition profileDefinition = new CasdoorProfileDefinition();
        profileDefinition.setProfileUrl(profileUrl);
        configuration.setProfileDefinition(profileDefinition);

        configuration.setScope(this.scope);
        defaultProfileCreator(new CasdoorProfileCreator(configuration, this));
        super.clientInit();
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }


}
