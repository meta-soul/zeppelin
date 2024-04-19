package org.apache.zeppelin.oauth2;

import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.engine.DefaultCallbackLogic;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.exception.http.RedirectionAction;
import org.pac4j.core.exception.http.SeeOtherAction;

import org.pac4j.core.util.Pac4jConstants;
import org.pac4j.core.exception.http.FoundAction;
import org.pac4j.core.util.CommonHelper;

public class ForceDefaultURLCallbackLogic extends DefaultCallbackLogic {
    private static boolean useModernHttpCodes = true;

    private static boolean alwaysUse401ForUnauthenticated = true;

    @Override
    public HttpAction redirectToOriginallyRequestedUrl(final WebContext context,
                                                          final String defaultUrl) {
        if(CommonHelper.isNotBlank(defaultUrl) && 
            !Pac4jConstants.DEFAULT_URL_VALUE.equals(defaultUrl)) {
            return buildRedirectUrlAction(context, ( new FoundAction(defaultUrl) ).getLocation());
        } else {
            return super.redirectToOriginallyRequestedUrl(context, "");
        }
    }

    /**
     * Build the appropriate redirection action for a location.
     *
     * @param context the web context
     * @param location the location
     * @return the appropriate redirection action
     */
    public static RedirectionAction buildRedirectUrlAction(final WebContext context, final String location) {
        if (isPost(context) && useModernHttpCodes) {
            return new SeeOtherAction(location);
        } else {
            return new FoundAction(location);
        }
    }

    public static boolean isPost(final WebContext context) {
        return HttpConstants.HTTP_METHOD.POST.name().equalsIgnoreCase(context.getRequestMethod());
    }
    
}