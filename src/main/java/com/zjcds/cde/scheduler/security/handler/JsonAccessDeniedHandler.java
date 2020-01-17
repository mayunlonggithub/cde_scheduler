package com.zjcds.cde.scheduler.security.handler;

import com.zjcds.cde.scheduler.common.BaseResponse;
import com.zjcds.cde.scheduler.common.ErrorEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 自定义json格式AccessDeniedHandler
 * @author：chenlb@zjcds.com
 * @date: 2019/9/8
 * @description 用来解决认证过的用户访问无权限资源时的异常
 */
@Component
public class JsonAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response,
                       final AccessDeniedException ex) {
        System.out.println("~~~没有权限呀？怎么肥四！~~~");
        new BaseResponse(ErrorEnum.ERROR9006, ex.getMessage()).writeAsJson(request,response);
    }

}
