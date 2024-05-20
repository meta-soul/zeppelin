package org.apache.zeppelin.oauth2.csair.prod;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.model.OAuth2AccessToken;
import org.pac4j.core.profile.ProfileHelper;
import org.pac4j.core.profile.converter.Converters;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.converter.JsonConverter;
import org.pac4j.oauth.profile.definition.OAuth20ProfileDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Set;

import static org.pac4j.core.profile.AttributeLocation.PROFILE_ATTRIBUTE;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasairProfileDefinition
 * @Description TODO
 * @createTime 2024/1/31 6:01
 */
public class CsairProfileDefinition extends OAuth20ProfileDefinition<CsairProfile, OAuth20Configuration> {
    private static  final Logger LOGGER = LoggerFactory.getLogger(CsairProfileDefinition.class);
    public static final String EMPLOYEENUMBER = "employeeNumber";
    public static final String LOGINNAME = "loginName";
    public static final String DISPLAYNAME = "displayName";
    public static final String SPROLELIST = "spRoleList";

    public CsairProfileDefinition(){
        super(x-> new CsairProfile());
        Arrays.stream(new String[]{
                EMPLOYEENUMBER,
                LOGINNAME,
                DISPLAYNAME
        }).forEach(a -> primary(a, Converters.STRING));

        primary(SPROLELIST, new JsonConverter<>(Set.class));
    }
    @Override
    public String getProfileUrl(OAuth2AccessToken oAuth2AccessToken, OAuth20Configuration oAuth20Configuration) {
        return "https://iam.csair.com/idp/oauth2/getUserInfo";
    }

    @Override
    public CsairProfile extractUserProfile(String body) {
        final CsairProfile profile = new CsairProfile();
        final JsonNode json = JsonHelper.getFirstNode(body);
        LOGGER.debug("User info is {}",json.toPrettyString());
        if (json != null) {
            profile.setId(ProfileHelper.sanitizeIdentifier(profile, JsonHelper.getElement(json, "uid")));
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
