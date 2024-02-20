package org.apache.zeppelin.oauth2.casdoor;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.Token;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileHelper;
import org.pac4j.core.profile.converter.Converters;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.config.OAuthConfiguration;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.converter.JsonConverter;
import org.pac4j.oauth.profile.definition.OAuth20ProfileDefinition;
import org.pac4j.oauth.profile.definition.OAuthProfileDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Set;

import static org.pac4j.core.profile.AttributeLocation.PROFILE_ATTRIBUTE;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasdoorProfileDefinition
 * @Description TODO
 * @createTime 2024/1/31 3:27
 */
public class CasdoorProfileDefinition extends OAuth20ProfileDefinition<CasdoorProfile, OAuth20Configuration> {
    private static  final Logger LOGGER = LoggerFactory.getLogger(CasdoorProfileDefinition.class);
    public static final String SUB = "sub";

    public static final String AUD = "aud"; //client_id
    public static final String ISS = "iss";
    public static final String PREFERRED_USERNAME = "preferred_username";
    public static final String NAME = "name";
    public static final String PICTURE = "picture";

    public static final String GROUPS = "groups";

    public CasdoorProfileDefinition(){
        super(x-> new CasdoorProfile());
        Arrays.stream(new String[]{
                SUB,
                ISS,
                PREFERRED_USERNAME,
                NAME
        }).forEach(a -> primary(a, Converters.STRING));
        primary(PICTURE, Converters.URL);
        primary(GROUPS, new JsonConverter<>(Set.class));
    }

    @Override
    public String getProfileUrl(OAuth2AccessToken oAuth2AccessToken, OAuth20Configuration oAuth20Configuration) {
        return "http://192.168.25.34:8000/api/userinfo";
    }




    @Override
    public CasdoorProfile extractUserProfile(String body) {
        LOGGER.debug("Extract UserProfile from userinfo interface");
        final CasdoorProfile profile = new CasdoorProfile();
        final JsonNode json = JsonHelper.getFirstNode(body);
        if (json != null) {
            profile.setId(ProfileHelper.sanitizeIdentifier(profile, JsonHelper.getElement(json, "sub")));
            for (final String attribute : getPrimaryAttributes()) {
                convertAndAdd(profile, PROFILE_ATTRIBUTE, attribute,
                        JsonHelper.getElement(json, attribute));
            }
        } else {
            raiseProfileExtractionJsonError(body);
        }
        LOGGER.debug("Extract UserProfile from userinfo is {}", profile.toString());
        return profile;
    }
}
