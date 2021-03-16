package com.cloume.maps.auth.filter;

import com.cloume.commons.rest.response.RestResponse;
import com.cloume.maps.commons.enums.ResultCode;
import com.cloume.maps.commons.utils.ResponseUtil;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    /**
     * 用户名和密码异常
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidGrantException.class)
    public RestResponse<?> handleInvalidGrantException(InvalidGrantException e) {
        return ResponseUtil.bad(ResultCode.USERNAME_OR_PASSWORD_ERROR);
    }


    /**
     * 账户异常(禁用、锁定、过期)
     * @param e
     * @return
     */
    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public RestResponse<?> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        return ResponseUtil.result(ResultCode.USER_ACCOUNT_LOCKED.getCode(), e.getMessage(), null);
    }

}
