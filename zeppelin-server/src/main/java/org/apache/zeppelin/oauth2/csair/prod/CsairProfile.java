package org.apache.zeppelin.oauth2.csair.prod;

import org.pac4j.oauth.profile.OAuth20Profile;

import java.util.Set;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName CasairProfile
 * @Description TODO
 * @createTime 2024/1/31 6:00
 */
public class CsairProfile extends OAuth20Profile {
    @Override
    public String getUsername() {
        return (String) getAttribute(CsairProfileDefinition.LOGINNAME);
    }

    @Override
    public String getDisplayName() {
        return (String) getAttribute(CsairProfileDefinition.DISPLAYNAME);

    }

    public String getEmployeeNumber(){
        return (String) getAttribute(CsairProfileDefinition.EMPLOYEENUMBER);
    }

    @Override
    public Set<String> getRoles() {
        return (Set<String>) getAttribute(CsairProfileDefinition.SPROLELIST);
    }
}
