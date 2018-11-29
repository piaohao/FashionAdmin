package org.piaohao.fashionadmin.constant;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResultCode {

    AUTH_EXPIRE(10010, "token过期"),
    AUTH_DENY(10011, "token无效"),

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
