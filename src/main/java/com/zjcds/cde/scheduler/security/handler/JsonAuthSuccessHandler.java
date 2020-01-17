package com.zjcds.cde.scheduler.security.handler;

import com.zjcds.cde.scheduler.common.BaseResponse;
import com.zjcds.cde.scheduler.utils.WebSecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义json格式successHandler
 *
 * @author：chenlb@zjcds.com
 * @date: 2019/9/8
 */
@Component
public class JsonAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        System.out.println("~~~哎呦喂！登录成功啦！~~~");

//        response.setHeader("cookie", request.getSession().getId());
        new BaseResponse(WebSecurityUtils.currentUser()).writeAsJson(request, response);

    }
}

