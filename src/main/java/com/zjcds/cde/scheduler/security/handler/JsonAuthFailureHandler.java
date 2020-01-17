package com.zjcds.cde.scheduler.security.handler;


import com.zjcds.cde.scheduler.common.BaseResponse;
import com.zjcds.cde.scheduler.common.ErrorEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 自定义json格式failureHandler
 * @author：chenlb@zjcds.com
 * @date: 2019/9/8
 */
@Component
public class JsonAuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) {
//        e.printStackTrace();

        System.out.println("~~~登录失败呀？怎么肥四！~~~");
        new BaseResponse(ErrorEnum.ERROR9004, e.getMessage()).writeAsJson(request,response);
    }
}
