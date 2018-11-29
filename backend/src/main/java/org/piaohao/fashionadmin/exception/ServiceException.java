package org.piaohao.fashionadmin.exception;

import lombok.Data;
import org.piaohao.fashionadmin.constant.ResultCode;

@Data
public class ServiceException extends RuntimeException {

    private ResultCode resultCode;

    public ServiceException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

}
