package com.cloume.maps.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xcai
 * @date 2021-03-10
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResultCode implements Serializable {

    SUCCESS(0000, "OK"),

    USER_ERROR(0001, "用户端错误"),
    USER_LOGIN_ERROR(0200, "用户登录异常"),

    USER_NOT_EXIST(0201, "用户不存在"),
    USER_ACCOUNT_LOCKED(0202, "用户账户被冻结"),
    USER_ACCOUNT_INVALID(0203, "用户账户已作废"),

    USERNAME_OR_PASSWORD_ERROR(0210, "用户名或密码错误"),
    INPUT_PASSWORD_EXCEED_LIMIT(0211, "用户输入密码次数超限"),
    CLIENT_AUTHENTICATION_FAILED(0212, "客户端认证失败"),
    TOKEN_INVALID_OR_EXPIRED(259, "token无效或已过期"),
    TOKEN_ACCESS_FORBIDDEN(0231, "token已被禁止访问"),

    AUTHORIZED_ERROR(0300, "访问权限异常"),
    UNAUTHORIZED(401, "访问未授权"),
    FORBIDDEN(403, "禁止访问"),
    FORBIDDEN_OPERATION(0302, "演示环境禁止修改、删除重要数据，请本地部署后测试"),


    PARAM_ERROR0400(0400, "用户请求参数错误"),
    PARAM_IS_NULL(0410, "请求必填参数为空"),
    QUERY_MODE_IS_NULL(0411, "查询模式为空"),

    USER_UPLOAD_FILE_ERROR(0700, "用户上传文件异常"),
    USER_UPLOAD_FILE_TYPE_NOT_MATCH(0701, "用户上传文件类型不匹配"),
    USER_UPLOAD_FILE_SIZE_EXCEEDS(0702, "用户上传文件太大"),
    USER_UPLOAD_IMAGE_SIZE_EXCEEDS(0703, "用户上传图片太大"),

    SYSTEM_EXECUTION_ERROR(0001, "系统执行出错"),
    SYSTEM_EXECUTION_TIMEOUT(0100, "系统执行超时"),
    SYSTEM_ORDER_PROCESSING_TIMEOUT(0100, "系统订单处理超时"),

    SYSTEM_DISASTER_RECOVERY_TRIGGER(0200, "系统容灾功能被出发"),
    SYSTEM_LIMITING(0210, "系统限流"),
    SYSTEM_FUNCTION_DEGRADATION(0220, "系统功能降级"),

    SYSTEM_RESOURCE_ERROR(0300, "系统资源异常"),
    SYSTEM_RESOURCE_EXHAUSTION(0310, "系统资源耗尽"),
    SYSTEM_RESOURCE_ACCESS_ERROR(0320, "系统资源访问异常"),
    SYSTEM_READ_DISK_FILE_ERROR(0321, "系统读取磁盘文件失败"),

    CALL_THIRD_PARTY_SERVICE_ERROR(0001, "调用第三方服务出错"),
    MIDDLEWARE_SERVICE_ERROR(0100, "中间件服务出错"),
    INTERFACE_NOT_EXIST(0113, "接口不存在"),

    MESSAGE_SERVICE_ERROR(0120, "消息服务出错"),
    MESSAGE_DELIVERY_ERROR(0121, "消息投递出错"),
    MESSAGE_CONSUMPTION_ERROR(0122, "消息消费出错"),
    MESSAGE_SUBSCRIPTION_ERROR(0123, "消息订阅出错"),
    MESSAGE_GROUP_NOT_FOUND(0124, "消息分组未查到"),

    DATABASE_ERROR(0300, "数据库服务出错"),
    DATABASE_TABLE_NOT_EXIST(0311, "表不存在"),
    DATABASE_COLUMN_NOT_EXIST(0312, "列不存在"),
    DATABASE_DUPLICATE_COLUMN_NAME(0321, "多表关联中存在多个相同名称的列"),
    DATABASE_DEADLOCK(0331, "数据库死锁"),
    DATABASE_PRIMARY_KEY_CONFLICT(0341, "主键冲突");

    private Integer code;

    private String msg;
}
