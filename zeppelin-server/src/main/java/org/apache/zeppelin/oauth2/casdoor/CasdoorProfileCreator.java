package org.apache.zeppelin.oauth2.casdoor;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.apache.zeppelin.realm.jwt.WorkspaceFilter;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.HttpCommunicationException;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.profile.creator.OAuth20ProfileCreator;
import org.pac4j.oauth.profile.qq.QQProfile;
import org.pac4j.oauth.profile.qq.QQProfileDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasdoorProfileCreator
 * @Description TODO
 * @createTime 2024/1/31 3:28
 */
public class CasdoorProfileCreator  extends OAuth20ProfileCreator<CasdoorProfile> {
    private static  final Logger LOGGER = LoggerFactory.getLogger(CasdoorProfileCreator.class);

    public CasdoorProfileCreator(OAuth20Configuration configuration, IndirectClient client) {
        super(configuration, client);
        LOGGER.debug("Create Casdoor ProfileCreator Success");
    }

    @Override
    protected Optional<UserProfile> retrieveUserProfileFromToken(WebContext context, OAuth2AccessToken accessToken) {
        LOGGER.debug("retrieveUserProfile in Casdoor ProfileCreator");
        CasdoorProfileDefinition profileDefinition = (CasdoorProfileDefinition) configuration.getProfileDefinition();

        final OAuth20Service service = this.configuration.buildService(context, client);


        String profileUrl = profileDefinition.getProfileUrl(accessToken, configuration);

        profileUrl = CommonHelper.addParameter(profileUrl, "client_id", configuration.getKey());
        String body = sendRequestForData(service, accessToken, profileUrl, Verb.GET);

        LOGGER.debug("retrieveUser Profile from with accessToken and client_id \n access_token is {}",accessToken);
        if (body == null) {
            throw new HttpCommunicationException("Not data found for accessToken: " + accessToken);
        }
        final CasdoorProfile profile = profileDefinition.extractUserProfile(body);
        addAccessTokenToProfile(profile, accessToken);
        LOGGER.debug("retrieveUserProfile is {}", profile.toString());
        return Optional.of(profile);
    }
}
