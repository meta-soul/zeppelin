package org.apache.zeppelin.oauth2.casair;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.apache.zeppelin.oauth2.casdoor.CasdoorProfile;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.HttpCommunicationException;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.profile.creator.OAuth20ProfileCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasairProfileCreator
 * @Description TODO
 * @createTime 2024/1/31 6:01
 */
public class CasairProfileCreator extends OAuth20ProfileCreator<CasairProfile> {
    private static  final Logger LOGGER = LoggerFactory.getLogger(CasairProfileCreator.class);
    public CasairProfileCreator(OAuth20Configuration configuration, IndirectClient client) {
        super(configuration, client);
    }

    @Override
    protected Optional<UserProfile> retrieveUserProfileFromToken(WebContext context, OAuth2AccessToken accessToken) {
        CasairProfileDefinition profileDefinition = (CasairProfileDefinition) configuration.getProfileDefinition();
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
        final CasairProfile profile = profileDefinition.extractUserProfile(body);
        addAccessTokenToProfile(profile, accessToken);
        LOGGER.debug("retrieveUserProfile is {}", profile.toString());
        return Optional.of(profile);
    }

}
