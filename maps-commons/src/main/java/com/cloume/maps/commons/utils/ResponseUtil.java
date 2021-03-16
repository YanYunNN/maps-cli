package com.cloume.maps.commons.utils;

import com.cloume.commons.rest.response.RestResponse;
import com.cloume.maps.commons.enums.ResultCode;

/**
 * @author xcai
 * @version 1.0
 * @date 2021/03/10/14:15
 * @description 对RestResponse的增强
 */
public class ResponseUtil<RT> {

    public static <T> RestResponse<T> result(int code, String message, T result) { return new RestResponse(code, message, result); }

    public static <T> RestResponse<T> good(T result) { return result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), result); }

    public static <T> RestResponse<T> bad(ResultCode resultCode) { return result(resultCode.getCode(), resultCode.getMsg(), (T) null); }

    public static <T> RestResponse<T> bad(ResultCode resultCode, T result) { return result(resultCode.getCode(), resultCode.getMsg(), result); }
}
