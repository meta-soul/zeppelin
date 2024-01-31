package org.apache.zeppelin.oauth2.casair;

import org.apache.zeppelin.oauth2.casdoor.CasdoorApi;
import org.apache.zeppelin.oauth2.casdoor.CasdoorProfileCreator;
import org.apache.zeppelin.oauth2.casdoor.CasdoorProfileDefinition;
import org.pac4j.oauth.client.OAuth20Client;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasairClient
 * @Description TODO
 * @createTime 2024/1/31 6:00
 */
public class CasairClient extends OAuth20Client {
    public CasairClient(){

    }

    public CasairClient(final String key, final String secret){
        setKey(key);
        setSecret(secret);
    }

    @Override
    protected void clientInit() {

        configuration.setApi(new CasairApi());
        configuration.setWithState(true);
        configuration.setProfileDefinition(new CasairProfileDefinition());

        defaultProfileCreator(new CasairProfileCreator(configuration, this));
        super.clientInit();
    }

}
