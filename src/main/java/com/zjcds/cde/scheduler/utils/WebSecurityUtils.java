package com.zjcds.cde.scheduler.utils;

import com.zjcds.cde.scheduler.domain.entity.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * created date：2017-02-10
 * @author niezhegang
 */
public class WebSecurityUtils {

    private static AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
    /**没有登录时的匿名用户名*/
    public final static String AnonymousUserName = "anonymous";

    public final static String RootUserRole = "root";

    private static SessionRegistry sessionRegistry;

    public static void initSessionRegistry(SessionRegistry sessionRegistry){
        if(WebSecurityUtils.sessionRegistry == null)
            WebSecurityUtils.sessionRegistry = sessionRegistry;
        else
            throw new IllegalArgumentException("SessionRegistry不允许重复初始化！");
    }

    public static void invalidateUserSession(User user) {
        if(sessionRegistry == null)
            throw new IllegalArgumentException("sessionRegistry未初始化！");
        List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions(user,false);
        if(CollectionUtils.isNotEmpty(sessionInformationList)){
            for (SessionInformation sessionInformation : sessionInformationList){
                sessionInformation.expireNow();
            }
        }
    }

    /**
     * 判定是否登录验证出错
     * @param request
     * @param response
     * @return
     */
    public static boolean loginPageError(HttpServletRequest request, HttpServletResponse response) {
        boolean bRet = false;
        String errorParamName = "error";
        if(request != null) {
            Map<String,String[]> params = request.getParameterMap();
            if(params != null && params.containsKey(errorParamName)){
                bRet = true;
            }
        }
        return bRet;
    }

    /**
     * print error message
     * @param request
     * @param response
     * @return
     */
    public static String printErrorMessage(HttpServletRequest request, HttpServletResponse response) {
       String message = "";
        if(loginPageError(request,response)){
            message = "用户名或密码错误！";
//            request.getAttribute();
        }
        return message;
    }

    /**
     * 判定当前用户是否已经登录
     * @return
     */
    public static boolean isLogined(){
        boolean bRet = false;
        if(SecurityContextHolder.getContext() != null &&
                SecurityContextHolder.getContext().getAuthentication() != null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(!authenticationTrustResolver.isAnonymous(authentication) &&
                    !authenticationTrustResolver.isRememberMe(authentication)){
                bRet = true;
            }
        }
        return bRet;
    }

    /**
     * 获取当前登录用户名
     * @return
     */
    public static String currentUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || StringUtils.isBlank(authentication.getName()))
            return AnonymousUserName;
        else {
            return authentication.getName();
        }
    }

    /**
     * 获取当前用户，如果未登录则返回null
     * @return
     */
    public static User currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof User)){
//            return null;
            // TODO: 2019-09-09 测试
            User user = new User();
            user.setAccount("张成基");
            user.setId(11);
            return user;
        } else {
            return (User) authentication.getPrincipal();
        }
    }

    /**
     * 判定是否是超级管理员角色
     * @return
     */
//    public static boolean currentUserIsRoot(){
//        return currentUserHasRole(RootUserRole);
//    }

    /**
     * 判定角色名是否是root用户
     * @param roleName
     * @return
     */
    public static boolean isRoot(String roleName){
        return StringUtils.equals(roleName,RootUserRole);
    }

    /**
     * 判断当前登录用户是否有特定角色
     * @param role
     * @return
     */
//    public static boolean currentUserHasRole(String role){
//        boolean bRet = false;
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication != null) {
//            Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
//            if(grantedAuthorities != null){
//                RoleGrantedAuthority roleGrantedAuthority = null;
//                for (GrantedAuthority grantedAuthority : grantedAuthorities){
//                    if(grantedAuthority instanceof RoleGrantedAuthority) {
//                        roleGrantedAuthority = (RoleGrantedAuthority) grantedAuthority;
//                        if(roleGrantedAuthority.equalRole(role)){
//                            bRet = true;
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//        return bRet;
//    }
    /**
     * 获取当前用户ID
     * @return
     */
    public static Integer currentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof User))
            return null;
        else {
            return ((User) authentication.getPrincipal()).getId();
        }
    }
}
