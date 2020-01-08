package com.zjcds.cde.scheduler.conf;

import com.zjcds.cde.scheduler.base.ResponseResult;
import com.zjcds.cde.scheduler.common.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zr
 * @description 全局异常响应
 * @date 25/12/2017
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    /**
//     * 拦截权限不足的异常
//     *
//     * @param request 请求
//     * @param e       异常
//     * @return 响应
//     */
//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler(AccessDeniedException.class)
//    public BaseResponse handleRuntimeException(HttpServletRequest request, AccessDeniedException e) {
//        e.printStackTrace();
//        logger.error("请求：{}, params:{}, 无权访问：{}", request.getRequestURI(), request.getParameterMap(), e);
//        return BaseResponse.buildError(ErrorEnum.ERROR9006, e.getMessage());
//    }


//    /**
//     * 拦截业务异常
//     *
//     * @param request 请求
//     * @param e       异常
//     * @return 响应
//     */
//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler(BusException.class)
//    public BaseResponse handleBusException(HttpServletRequest request, BusException e) {
//        e.printStackTrace();
//        logger.error("请求：{}, params:{}, 业务异常：{}", request.getRequestURI(), request.getParameterMap().toString(), e);
//        return new BaseResponse(e.getBusErrorEnum(), e.getMessage());
//    }

//    /**
//     * 拦截绑定异常
//     *
//     * @param request 请求
//     * @param e       异常
//     * @return 响应
//     */
//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
//    public ResponseResult handleRequestBindException(HttpServletRequest request, MethodArgumentNotValidException e) {
//        BindingResult bindingResult = e.getBindingResult();
//        Map<String, String> errorMap = bindingResult.getFieldErrors()
//                .stream()
//                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (v1, v2) -> v1));
//        logger.error("请求：{}, params:{}, 参数校验错误：{}", request.getRequestURI(), request.getParameterMap().toString(), e);
//        return new ResponseResult(ErrorEnum.ERROR9002, errorMap);
//    }

//    /**
//     * 拦截无效请求异常
//     *
//     * @param request 请求
//     * @param e       异常
//     * @return 响应
//     */
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseResult handleNotFoundException(HttpServletRequest request, NoHandlerFoundException e) {
//        logger.error("请求：{}, params:{}, 无效请求错误：{}", request.getRequestURI(), request.getParameterMap().toString(), e);
//        return new ResponseResult(ErrorEnum.ERROR9001);
//    }
//
//    /**
//     * 拦截无效请求异常
//     *
//     * @param request 请求
//     * @param e       异常
//     * @return 响应
//     */
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public ResponseResult handleUnsupportedMethodException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
//        logger.error("请求：{}, params:{}, 无效请求错误：{}", request.getRequestURI(), request.getParameterMap().toString(), e);
//        return new ResponseResult(ErrorEnum.ERROR9001);
//    }
//
//    /**
//     * 拦截服务器异常
//     *
//     * @param request 请求
//     * @param e       异常
//     * @return 响应
//     * @throws RuntimeException 抛出异常用于事务回滚
//     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseResult handleRuntimeException(HttpServletRequest request, RuntimeException e) throws RuntimeException {
//        e.printStackTrace();
//        logger.error("请求：{}, params:{}, 数据库响应错误：{}", request.getRequestURI(), request.getParameterMap().toString(), e);
//        return new ResponseResult(false,e.getMessage());
//    }

    /**
     * 拦截Assert断言
     *
     * @param request 请求
     * @param e       异常
     * @return 响应
     * @throws IllegalArgumentException 抛出Assert断言
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseResult IllegalArgumentException(HttpServletRequest request, HttpServletResponse response, RuntimeException e) throws RuntimeException {
        e.printStackTrace();
        logger.error("请求：{}, params:{}, 参数错误：{}", request.getRequestURI(), request.getParameterMap().toString(), e);
        response.setStatus(HttpStatus.OK.value());
        return new ResponseResult(true,e.getMessage());
    }



//    /**
//     * 拦截服务器异常
//     *
//     * @param request 请求
//     * @param e       异常
//     * @return 响应
//     */
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public ResponseResult handleException(HttpServletRequest request, Exception e) {
//        logger.error("请求：{}, params:{}, 服务器响应错误：{}", request.getRequestURI(), request.getParameterMap().toString(), e);
//        return new ResponseResult(ErrorEnum.ERROR9000);
//    }

}
