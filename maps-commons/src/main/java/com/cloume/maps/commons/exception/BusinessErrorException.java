package com.cloume.maps.commons.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义业务异常
 * @author xcai
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessErrorException extends RuntimeException {
    private static final long serialVersionUID = -7480022450501760611L;
    /**
     * 异常码
     */
    private String code;
    /**
     * 异常提示信息
     */
    private String message;

    public BusinessErrorException(BusinessMsgEnum businessMsgEnum) {
        this.code = businessMsgEnum.getCode();
        this.message = businessMsgEnum.getMsg();
    }
}