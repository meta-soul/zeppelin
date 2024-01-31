package org.apache.zeppelin.oauth2.casdoor;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.HttpCommunicationException;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.profile.creator.OAuth20ProfileCreator;
import org.pac4j.oauth.profile.qq.QQProfile;
import org.pac4j.oauth.profile.qq.QQProfileDefinition;

import java.util.Optional;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasdoorProfileCreator
 * @Description TODO
 * @createTime 2024/1/31 3:28
 */
public class CasdoorProfileCreator  extends OAuth20ProfileCreator<CasdoorProfile> {
    public CasdoorProfileCreator(OAuth20Configuration configuration, IndirectClient client) {
        super(configuration, client);
    }

    @Override
    protected Optional<UserProfile> retrieveUserProfileFromToken(WebContext context, OAuth2AccessToken accessToken) {

        CasdoorProfileDefinition profileDefinition = (CasdoorProfileDefinition) configuration.getProfileDefinition();

        final OAuth20Service service = this.configuration.buildService(context, client);


        String profileUrl = profileDefinition.getProfileUrl(accessToken, configuration);

        profileUrl = CommonHelper.addParameter(profileUrl, "client_id", configuration.getKey());
        String body = sendRequestForData(service, accessToken, profileUrl, Verb.GET);
        if (body == null) {
            throw new HttpCommunicationException("Not data found for accessToken: " + accessToken);
        }
        final CasdoorProfile profile = profileDefinition.extractUserProfile(body);
        addAccessTokenToProfile(profile, accessToken);

        return Optional.of(profile);
    }
}
