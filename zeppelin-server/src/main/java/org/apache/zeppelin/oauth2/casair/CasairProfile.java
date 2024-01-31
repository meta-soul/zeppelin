package org.apache.zeppelin.oauth2.casair;

import org.apache.zeppelin.oauth2.casdoor.CasdoorProfileDefinition;
import org.pac4j.oauth.profile.OAuth20Profile;

import java.util.Set;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasairProfile
 * @Description TODO
 * @createTime 2024/1/31 6:00
 */
public class CasairProfile extends OAuth20Profile {
    @Override
    public String getUsername() {
        return (String) getAttribute(CasairProfileDefinition.EMPLOYEENUMBER);
    }

    @Override
    public String getDisplayName() {
        return (String) getAttribute(CasairProfileDefinition.LOGINNAME);
    }

    @Override
    public Set<String> getRoles() {
        return (Set<String>) getAttribute(CasairProfileDefinition.SPROLELIST);
    }
}
