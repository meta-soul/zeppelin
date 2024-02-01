package org.apache.zeppelin.oauth2.casdoor;

import org.pac4j.oauth.client.OAuth20Client;
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

        configuration.setApi(CasdoorApi.instance());
        configuration.setWithState(false);
        configuration.setProfileDefinition(new CasdoorProfileDefinition());
        configuration.setScope(this.scope);
        defaultProfileCreator(new CasdoorProfileCreator(configuration, this));
        super.clientInit();
    }



}
