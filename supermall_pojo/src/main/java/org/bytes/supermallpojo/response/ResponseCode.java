package org.bytes.supermallpojo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 项目共用返回状态码
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {

    /**
     * 0成功
     */
    SUCCESS(0, "SUCCESS"),
    /**
     * 1未知异常
     */
    UNKNOWN_ERROR(1, "服务器错误"),
    /**
     * 10001用户不存在
     */
    USER_NOT_EXIST(10001, "用户不存在"),
    /**
     * 10002令牌已过期
     */
    TOKEN_EXPIRED(10002, "令牌已过期"),
    /**
     * 10003令牌无效
     */
    TOKEN_INVALID(10003, "令牌无效"),
    /**
     * 10004令牌已注销
     */
    TOKEN_REVOKED(10004, "令牌已注销"),
    /**
     * 10005邮箱已存在
     */
    EMAIL_EXIST(10005, "邮箱已存在"),
    /**
     * 10006邮箱不存在
     */
    EMAIL_NOT_EXIST(10006, "邮箱不存在"),
    /**
     * 10007密码错误
     */
    PASSWORD_ERROR(10007, "密码错误"),
    /**
     * 10009商品不存在
     */
    PRODUCT_NOT_EXIST(10009, "商品不存在"),
    /**
     * 货币不支持10016
     */
    CURRENCY_NOT_SUPPORT(10016, "货币不支持"),
    /**
     * 10017订单创建失败
     */
    ORDER_CREATE_FAIL(10017, "订单创建失败"),
    /**
     * 订单不存在10019
     */
    ORDER_NOT_EXIST(10019, "订单不存在"),
    /**
     * 支付失败10018
     */
    PAYMENT_FAIL(10018, "支付失败");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 状态码描述
     */
    private final String msg;

}
