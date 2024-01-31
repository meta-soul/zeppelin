package org.apache.zeppelin.oauth2.casdoor;

import org.pac4j.oauth.client.OAuth20Client;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasdoorClient
 * @Description TODO
 * @createTime 2024/1/31 3:30
 */
public class CasdoorClient extends OAuth20Client {
    protected final String scope = "openid+profile+email";
    public CasdoorClient(){

    }

    public CasdoorClient(final String key, final String secret){
        setKey(key);
        setSecret(secret);
    }

    @Override
    protected void clientInit() {

        configuration.setApi(new CasdoorApi());
        configuration.setWithState(true);
        configuration.setProfileDefinition(new CasdoorProfileDefinition());
        configuration.setScope(this.scope);
        defaultProfileCreator(new CasdoorProfileCreator(configuration, this));
        super.clientInit();
    }
}
