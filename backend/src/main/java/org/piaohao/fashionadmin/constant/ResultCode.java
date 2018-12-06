package org.piaohao.fashionadmin.constant;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResultCode {

    AUTH_TOKEN_EXPIRE(10010, "token过期"),
    AUTH_TOKEN_DENY(10011, "token无效"),
    AUTH_FAILED(10012, "用户名或密码错误"),
    AUTH_QUERY_NOT_VALID(10013, "用户名或密码不能为空"),
    AUTH_TOKEN_GENERATE_FAILED(10014, "token生成异常"),
    USER_NOT_EXISTS(10015, "用户不存在"),
    USER_NAME_CONFLICT(10016, "用户名冲突"),

    DEFAULT_ERROR(10000, "系统繁忙");

    @Getter
    private int code;
    @Getter
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String json() {
        return JSONUtil.toJsonStr(this);
    }

}
