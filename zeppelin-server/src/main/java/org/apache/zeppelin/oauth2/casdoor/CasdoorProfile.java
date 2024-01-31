package org.apache.zeppelin.oauth2.casdoor;

import org.pac4j.oauth.profile.OAuth20Profile;

import java.net.URI;


/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasdoorProfile
 * @Description TODO
 * @createTime 2024/1/31 3:27
 */
public class CasdoorProfile extends OAuth20Profile {

    @Override
    public String getUsername() {
        return (String) getAttribute(CasdoorProfileDefinition.SUB);
    }

    @Override
    public String getEmail() {
        return (String) getAttribute(CasdoorProfileDefinition.EMAIL);
    }

    @Override
    public String getDisplayName() {
        return (String) getAttribute(CasdoorProfileDefinition.NAME);
    }

    @Override
    public URI getProfileUrl() {
        return (URI) getAttribute(CasdoorProfileDefinition.PICTURE);
    }

}
