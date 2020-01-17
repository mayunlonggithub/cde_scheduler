package com.zjcds.cde.scheduler.security.handler;

import com.zjcds.cde.scheduler.common.BaseResponse;
import com.zjcds.cde.scheduler.common.ErrorEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 自定义json格式AuthenticationEntryPoint
 * @author：chenlb@zjcds.com
 * @date: 2019/9/8
 * @description 用来解决匿名用户访问无权限资源时的异常
 */
@Component
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
//        authException.printStackTrace();
        System.out.println("~~~没有登录呀？怎么肥四！~~~");
        new BaseResponse(ErrorEnum.ERROR9005, authException.getMessage()).writeAsJson(request,response);
    }
}
