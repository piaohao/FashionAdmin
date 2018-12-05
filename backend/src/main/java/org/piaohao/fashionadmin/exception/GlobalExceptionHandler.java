package org.piaohao.fashionadmin.exception;

import lombok.extern.slf4j.Slf4j;
import org.piaohao.fashionadmin.constant.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object jsonErrorHandler(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        return ResultCode.DEFAULT_ERROR;
    }

    /*@ExceptionHandler(ServiceException.class)
    public void exception(ServiceException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        int code = e.getResultCode().getCode();
        response.setContentType(ContentType.JSON.toString());
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        if (code == ResultCode.AUTH_DENY.getCode() || code == ResultCode.AUTH_EXPIRE.getCode()) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        PrintWriter writer = response.getWriter();
        writer.write(e.getResultCode().json());
        writer.close();
    }*/

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResultCode exception(ServiceException e, HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        return e.getResultCode();
    }

}
