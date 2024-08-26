package org.apache.zeppelin.oauth2.csair.prod;


import org.pac4j.oauth.client.OAuth20Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasairClient
 * @Description TODO
 * @createTime 2024/1/31 6:00
 */
public class CsairClient extends OAuth20Client {
    private static  final Logger LOGGER = LoggerFactory.getLogger(CsairClient.class);
    private String authUrl;
    private String tokenUrl;
    private String profileUrl;

    public CsairClient(){

    }

    public CsairClient(final String key, final String secret){
        setKey(key);
        setSecret(secret);
    }

    @Override
    protected void clientInit() {
        LOGGER.debug("Casair init Start");
        configuration.setApi(new CsairApi(authUrl, tokenUrl));
        configuration.setWithState(false);
        CsairProfileDefinition profileDefinition = new CsairProfileDefinition();
        profileDefinition.setProfileUrl(profileUrl);
        configuration.setProfileDefinition(profileDefinition);

        defaultProfileCreator(new CsairProfileCreator(configuration, this));
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
