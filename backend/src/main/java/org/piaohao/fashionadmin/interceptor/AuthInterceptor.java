package org.piaohao.fashionadmin.interceptor;

import org.piaohao.fashionadmin.annotation.ClearAuth;
import org.piaohao.fashionadmin.util.JwtUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(ClearAuth.class)) {
            return true;
        }
        Map<String, String> info = null;
        info = JwtUtil.verifyToken(token);
        if (!info.get("id").equals("111111")) {
            return false;
        }
        return true;
    }
}
