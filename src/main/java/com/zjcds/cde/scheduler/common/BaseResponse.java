package com.zjcds.cde.scheduler.common;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @author zr
 * @description 基础返回类
 * @date 21/12/2017
 */
public class BaseResponse<T> implements Serializable {

    private Logger logger = LoggerFactory.getLogger(BaseResponse.class);

    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final Integer SUCCESS = 200;
    /**
     * 失败
     */
    public static final Integer ERROR = 400;
    /**
     * 异常
     */
    public static final Integer EXCEPTION = 500;

    /**
     * 请求状态码
     */
    private Integer status = 200;

    /**
     * 错误信息
     */
    private ErrorEnum errorEnum;

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 数据
     */
    private T data;

    /**
     * @param errorEnum 错误信息
     */
    public BaseResponse(ErrorEnum errorEnum) {
        this.status = ERROR;
        this.errorEnum = errorEnum;
        if (errorEnum != null) {
            this.errorCode = errorEnum.getErrorCode();
            this.errorMsg = errorEnum.getErrorMsg();
        }
    }

    /**
     * @param errorEnum 错误信息
     * @param errorMsg  错误描述
     */
    public BaseResponse(ErrorEnum errorEnum, String errorMsg) {
        this.status = ERROR;
        this.errorEnum = errorEnum;
        this.errorMsg = errorMsg;
        if (errorEnum != null) {
            this.errorCode = errorEnum.getErrorCode();
            this.errorMsg = errorEnum.getErrorMsg();
        }
    }


    /**
     * @param errorEnum 错误信息
     * @param data      对象
     */
    public BaseResponse(ErrorEnum errorEnum, T data) {
        this.data = data;
        this.status = ERROR;
        this.errorEnum = errorEnum;
        if (errorEnum != null) {
            this.errorCode = errorEnum.getErrorCode();
            this.errorMsg = errorEnum.getErrorMsg();
        }
    }

    /**
     * @param data 返回数据
     */
    public BaseResponse(T data) {
        this.data = data;
    }

    /**
     * 状态
     *
     * @param status 状态
     */
    public BaseResponse(Integer status) {
        this.status = status;
    }

    /**
     * 空构造器
     */
    public BaseResponse() {

    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonIgnore
    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public void setErrorEnum(ErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
        this.errorCode = errorEnum.getErrorCode();
        this.errorMsg = errorEnum.getErrorMsg();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        JSONObject jsonData = new JSONObject();
        jsonData.put("status", this.getStatus());
        if (this.errorEnum != null) {
            jsonData.put("errorCode", this.getErrorCode());
            jsonData.put("errorMsg", this.getErrorMsg());
        }
        if (null == data) {
            jsonData.put("data", "");
        } else {
            jsonData.put("data", data);
        }
        return jsonData.toJSONString();
    }

    /**
     * 将自身当成json串写出去
     *
     * @param response 响应
     * @return json报文
     */
    public void writeAsJson(HttpServletResponse response) {
        String jsonStr = this.toString();

        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        //允许ajax跨域的参数设置
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,Authorization");//表明服务器支持的所有头信息字段
        PrintWriter outter = null;

        try {
            outter = response.getWriter();
            outter.write(jsonStr);
            outter.flush();
            //打印日志
            logger.info("response jsonData:" + jsonStr);
        } catch (IOException e) {
            logger.debug("写出response jsonData报错：", e);
        } finally {
            if (outter != null) {
                outter.close();
            }
        }
    }

    /**
     * 将自身当成json串写出去
     *
     * @param response 响应
     * @return json报文
     */
    public void writeAsJson(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = this.toString();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        //允许ajax跨域的参数设置
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,Authorization");//表明服务器支持的所有头信息字段
        PrintWriter outter = null;
        try {
            outter = response.getWriter();
            outter.write(jsonStr);
            outter.flush();
            //打印日志
            logger.info("response jsonData:" + jsonStr);
        } catch (IOException e) {
            logger.debug("写出response jsonData报错：", e);
        } finally {
            if (outter != null) {
                outter.close();
            }
        }
    }



    /**
     * 将自身当成TextHtml写出去
     *
     * @param response 响应
     * @return html报文
     */
    public void writeAsTextHtml(HttpServletResponse response) {
        String jsonStr = this.toString();

        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        //允许ajax跨域的参数设置
        PrintWriter outter = null;
        try {
            outter = response.getWriter();
            outter.write(jsonStr);
            outter.flush();
            //打印日志
            logger.info("response TextHtml:" + jsonStr);
        } catch (IOException e) {
            logger.debug("写出response TextHtml报错：", e);
        } finally {
            if (outter != null) {
                outter.close();
            }
        }
    }

//    /**
//     * 写出成功响应
//     */
//    public static BaseResponse buildSuccess() {
//        return new BaseResponse(BaseResponse.SUCCESS);
//    }
//
//    /**
//     * 写出成功响应
//     *
//     * @param data 数据
//     */
//    public static BaseResponse buildSuccess(Object data) {
//        BaseResponse baseResponse = new BaseResponse(BaseResponse.SUCCESS);
//        baseResponse.setData(data);
//        return baseResponse;
//    }
//
//    /**
//     * 写出失败响应
//     *
//     * @param errorEnum 错误信息
//     */
//    public static BaseResponse buildError(ErrorEnum errorEnum) {
//        return new BaseResponse(BaseResponse.ERROR, errorEnum);
//    }
//
//    /**
//     * 写出失败响应
//     *
//     * @param errorEnum 错误信息
//     * @param data      错误详细结构 用于参数校验的详细信息
//     */
//    public static BaseResponse buildError(ErrorEnum errorEnum, Object data) {
//        BaseResponse baseResponse = new BaseResponse();
//        baseResponse.setStatus(BaseResponse.ERROR);
//        baseResponse.setErrorEnum(errorEnum);
//        baseResponse.setData(data);
//        return baseResponse;
//    }
//
//    /**
//     * 写出失败响应
//     *
//     * @param errorEnum 错误信息
//     * @param errorMsg  错误信息
//     */
//    public static BaseResponse buildError(ErrorEnum errorEnum, String errorMsg) {
//        errorEnum.setErrorMsg(errorMsg);
//        return new BaseResponse(BaseResponse.ERROR, errorEnum);
//    }
//
//    /**
//     * 写出异常响应
//     *
//     * @param errorEnum 错误信息
//     */
//    public static BaseResponse buildException(ErrorEnum errorEnum) {
//        return new BaseResponse(BaseResponse.EXCEPTION, errorEnum);
//    }
}
