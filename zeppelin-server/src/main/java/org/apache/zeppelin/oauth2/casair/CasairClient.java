package org.apache.zeppelin.oauth2.casair;


import org.pac4j.oauth.client.OAuth20Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasairClient
 * @Description TODO
 * @createTime 2024/1/31 6:00
 */
public class CasairClient extends OAuth20Client {
    private static  final Logger LOGGER = LoggerFactory.getLogger(CasairClient.class);

    private String authUrl;
    private String tokenUrl;
    private String profileUrl;



    public CasairClient(){

    }

    public CasairClient(final String key, final String secret){
        setKey(key);
        setSecret(secret);
    }

    @Override
    protected void clientInit() {
        LOGGER.debug("Casair init Start");
        configuration.setApi(new CasairApi(authUrl, tokenUrl));
        configuration.setWithState(false);
        CasairProfileDefinition profileDefinition = new CasairProfileDefinition();
        profileDefinition.setProfileUrl(profileUrl);
        configuration.setProfileDefinition(profileDefinition);

        defaultProfileCreator(new CasairProfileCreator(configuration, this));
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
