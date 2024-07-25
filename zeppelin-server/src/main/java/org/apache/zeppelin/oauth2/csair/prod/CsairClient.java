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
    public CsairClient(){

    }

    public CsairClient(final String key, final String secret){
        setKey(key);
        setSecret(secret);
    }

    @Override
    protected void clientInit() {
        LOGGER.debug("Casair init Start");
        configuration.setApi(CsairApi.instance());
        configuration.setWithState(false);
        configuration.setProfileDefinition(new CsairProfileDefinition());

        defaultProfileCreator(new CsairProfileCreator(configuration, this));
        super.clientInit();
    }

}
