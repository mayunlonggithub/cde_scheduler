package com.zjcds.cde.scheduler.common;

/**
 * Created by shihuajie on 2019/08/06
 */
public enum ErrorEnum {

    /**
     * 单点认证相关
     */
    ERROR1000("1000", "用户名或密码错误"),
    ERROR1001("1001", "手机号码无效"),
    ERROR1002("1002", "手机号码已注册"),
    ERROR1003("1003", "手机号码未注册"),
    ERROR1004("1004", "登录密码不正确为空或者不符合要求,6-15位,非纯数字"),
    ERROR1005("1005", "验证码有误或已失效"),
    ERROR1006("1006", "用户暂未分配权限"),
    ERROR1007("1007", "用户信息不完整"),
    ERROR1008("1008", "原密码输入错误"),
    ERROR1009("1009", "两次输入密码不一致，且不能与原密码一样"),

    /**
     * 系统管理相关
     */
    ERROR2000("2000", "用户信息不存在"),
    ERROR2001("2001", "用户信息填写不正确"),
    ERROR2002("2002", "上级部门已停用"),
    ERROR2003("2003", "部门信息不存在"),
    ERROR2004("2004", "部门信息填写不正确"),
    ERROR2005("2005", "菜单信息不存在"),
    ERROR2006("2006", "上级菜单已停用"),
    ERROR2007("2007", "角色信息不存在"),
    ERROR2008("2008", "帐号不可重复"),
    ERROR2009("2009", "平台信息不存在"),
    ERROR2010("2010", "部门名称不可重复"),
    ERROR2011("2011", "角色名称不可重复"),
    ERROR2012("2012", "父级部门选择有误，不能选择自己作为自己的父级"),
    ERROR2013("2013", "父级菜单选择有误，不能选择自己作为自己的父级"),
    ERROR2014("2014", "oauth客户端信息已经存在"),
    ERROR2015("2015", "oauth客户端操作失败，数据库异常"),
    ERROR2016("2016", "操作失败，客户端信息不完整"),
    ERROR2017("2017", "修改应用密钥信息失败，应用不存在"),
    ERROR2018("2018", "节点删除失败，节点不存在"),
    ERROR2019("2019", "节点树构建失败，根节点不存在"),
    ERROR2020("2020", "非应用节点不能收藏"),
    ERROR2021("2021", "该应用已被收藏"),
    ERROR2022("2022", "无权移除收藏"),
    ERROR2023("2023", "应用未被收藏"),
    ERROR2024("2024", "查看密钥失败，应用不存在"),
    ERROR2025("2025", "查看密钥失败，非应用节点"),
    ERROR2026("2026", "日志模板不存在"),

    /**
     * 文件相关
     */
    ERROR8000("8000", "文件关联的业务类型不存在"),
    ERROR8001("8001", "文件读取异常"),
    ERROR8002("8002", "云存储服务异常"),


    /**
     * 全局的异常
     */
    ERROR9000("9000", "服务器响应异常"),
    ERROR9001("9001", "无效请求"),
    ERROR9002("9002", "参数不符合约定"),
    ERROR9003("9003", "数据库操作异常"),
    ERROR9004("9004", "无效的登录信息或者登录失效，请重新登录"),
    ERROR9005("9005", "非法请求"),
    ERROR9006("9006", "没有操作权限"),
    ERROR9007("9007", "此操作不被允许"),

    /**
     * 业务异常
     */
    ERR_ID_NULL("B0001", "数据id不能为空"),
    ERR_DELETE("B0002", "无法注销"),
    ERR_SERVICE_IS_EXSIT("B0003", "服务已经存在"),
    ERR_ESB("B0004", "东方通错误"),
    ERR_SERVICE_INTENTION_IS_EXSIT("B0005", "服务意向已经存在"),

    ERR_SERVICE_IS_ON("B0006", "有服务资源存在"),
    ERR_NO_DATA_SOURCE("B0007", "未找到相关数据源"),
    ERR_NO_DATA_TABLE("B0008", "未找到数据表信息"),
    ERR_SQL_EXCEPTION("B0009", "数据查询失败"),
    ERR_SCHEDULE("B0010", "创建调度任务异常"),
    ERR_CREATE_TABLE("B0011", "动态建表失败"),
    ERR_ADD_DATA("B0012", "新增资源失败"),
    ERR_HDFS("B0013", "HDFS错误"),
    ERR_RES_IS_EXIST("B0014", "该目录已发布"),
    ERR_COLUMN_NULL("B0015", "字段列表不能为空"),
    ERR_DXP("B0016", "TongDXP异常"),

    /**
     * Activiti业务异常
     */
    ERR_ACTIVITI_PROCESS_START("B9001", "启动流程失败"),
    ERR_ACTIVITI_AUDIT("B9002", "审核失败"),
    ERR_ACTIVITI_AUDIT_ROLLBACK("B9003", "资源审核打回失败");

    /**
     * 错误码
     */
    private String errorCode;
    /**
     * 错误信息
     */
    private String errorMsg;

    ErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
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

    @Override
    public String toString() {
        return "[" + this.name() + "] : {errorCode:[" + this.getErrorCode() + "],errorMsg:[" + this.getErrorMsg() + "]}";
    }
}
