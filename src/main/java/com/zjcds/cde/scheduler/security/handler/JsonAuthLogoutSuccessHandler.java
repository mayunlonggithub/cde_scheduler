package com.zjcds.cde.scheduler.security.handler;

import com.zjcds.cde.scheduler.common.BaseResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义json格式logoutSuccessHandler
 * @author：chenlb@zjcds.com
 * @date: 2019/9/8
 */
@Component
public class JsonAuthLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        System.out.println("~~~啊哦！居然逃跑了！~~~");
        new BaseResponse().writeAsJson(request,response);
    }
}
