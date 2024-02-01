package org.apache.zeppelin.realm.jwt;

import com.google.common.base.Preconditions;
import io.buji.pac4j.profile.ShiroProfileManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.zeppelin.oauth2.casdoor.CasdoorProfile;
import org.dmetasoul.lakesoul.DBUtils;
import org.pac4j.core.context.JEEContext;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author Asakiny@dmetasoul.com
 * @ClassName WorkspaceFilter
 * @Description TODO
 * @createTime 2024/1/28 17:51
 */

public class WorkspaceFilter implements Filter {
    private static  final Logger LOGGER = LoggerFactory.getLogger(WorkspaceFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        String queryString = httpRequest.getQueryString();
        LOGGER.debug("Url Path is : {}", path );
        LOGGER.debug("Query String is {}", queryString);

        String workspace = httpRequest.getParameter("workspace");
        boolean isInWorkspace = false;
        if(workspace != null && path.equals("/")){
            JEEContext context = new JEEContext((HttpServletRequest) request, (HttpServletResponse) response);
            ShiroProfileManager profileManager = new ShiroProfileManager(context);
            Optional<CommonProfile> profile = profileManager.get(true);
            LOGGER.debug("profile is: {}", profile.get().toString());

            String userId = profile.get().getId();
            String userName = profile.get().getDisplayName();

            LOGGER.debug("WorkspaceFilter get user {} workspace is {}",userName, workspace);
            try {
                isInWorkspace = DBUtils.isUserInWorkSpace(userId, workspace);
                LOGGER.debug("Current user {} in Workspace {} is {}", userName, workspace, isInWorkspace);
                if(!isInWorkspace) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/404.html");
                    return;
                }
            } catch (SQLException e) {
                LOGGER.error("Query User {} in WorkSpace {} in WorkspaceFilter Error", userName, workspace);
                throw new RuntimeException(e);
            }
        }else if (workspace == null && path.equals("/")){
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/404.html");
            return;
        }

       filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }

}
