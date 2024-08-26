package org.apache.zeppelin.oauth2.casair;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.model.OAuth2AccessToken;
import org.apache.zeppelin.oauth2.casdoor.CasdoorProfile;
import org.pac4j.core.profile.ProfileHelper;
import org.pac4j.core.profile.converter.Converters;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.converter.JsonConverter;
import org.pac4j.oauth.profile.definition.OAuth20ProfileDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.pac4j.core.profile.AttributeLocation.PROFILE_ATTRIBUTE;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasairProfileDefinition
 * @Description TODO
 * @createTime 2024/1/31 6:01
 */
public class CasairProfileDefinition extends OAuth20ProfileDefinition<CasairProfile, OAuth20Configuration> {
    private static  final Logger LOGGER = LoggerFactory.getLogger(CasairProfileDefinition.class);
    public static final String EMPLOYEENUMBER = "employeeNumber";
    public static final String LOGINNAME = "loginName";
    public static final String DISPLAYNAME = "displayName";
    public static final String SPROLELIST = "spRoleList";

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    private String profileUrl;

    public CasairProfileDefinition(){
        super(x-> new CasairProfile());
        Arrays.stream(new String[]{
                EMPLOYEENUMBER,
                LOGINNAME,
                DISPLAYNAME
        }).forEach(a -> primary(a, Converters.STRING));

        primary(SPROLELIST, new JsonConverter<>(Set.class));
    }
    @Override
    public String getProfileUrl(OAuth2AccessToken oAuth2AccessToken, OAuth20Configuration oAuth20Configuration) {
        return this.getProfileUrl();
    }

    @Override
    public CasairProfile extractUserProfile(String body) {
        final CasairProfile profile = new CasairProfile();
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
