package org.apache.zeppelin.oauth2.csair.prod;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasairProfileCreator
 * @Description TODO
 * @createTime 2024/1/31 6:01
 */
public class CsairProfileCreator extends OAuth20ProfileCreator<CsairProfile> {
    private static  final Logger LOGGER = LoggerFactory.getLogger(CsairProfileCreator.class);
    public CsairProfileCreator(OAuth20Configuration configuration, IndirectClient client) {
        super(configuration, client);
    }

    @Override
    protected Optional<UserProfile> retrieveUserProfileFromToken(WebContext context, OAuth2AccessToken accessToken) {
        CsairProfileDefinition profileDefinition = (CsairProfileDefinition) configuration.getProfileDefinition();
        final OAuth20Service service = this.configuration.buildService(context, client);
        LOGGER.debug("retriveUserProfile from Token {}", accessToken.getAccessToken().toString());

        String profileUrl = profileDefinition.getProfileUrl(accessToken, configuration);

        profileUrl = CommonHelper.addParameter(profileUrl, "client_id", configuration.getKey());
        profileUrl = CommonHelper.addParameter(profileUrl, "access_token", accessToken.getAccessToken());
        String body = sendRequestForData(service, accessToken, profileUrl, Verb.GET);
        LOGGER.debug("Response Token is {}", body);
        if (body == null) {
            throw new HttpCommunicationException("Not data found for accessToken: " + accessToken);
        }
        final CsairProfile profile = profileDefinition.extractUserProfile(body);
        addAccessTokenToProfile(profile, accessToken);
        LOGGER.debug("retrieveUserProfile is {}", profile.toString());
        return Optional.of(profile);
    }

}
