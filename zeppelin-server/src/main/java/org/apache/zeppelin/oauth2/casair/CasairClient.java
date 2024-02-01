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
    public CasairClient(){

    }

    public CasairClient(final String key, final String secret){
        setKey(key);
        setSecret(secret);
    }

    @Override
    protected void clientInit() {
        LOGGER.debug("Casair init Start");
        configuration.setApi(CasairApi.instance());
        configuration.setWithState(false);
        configuration.setProfileDefinition(new CasairProfileDefinition());

        defaultProfileCreator(new CasairProfileCreator(configuration, this));
        super.clientInit();
    }

}
