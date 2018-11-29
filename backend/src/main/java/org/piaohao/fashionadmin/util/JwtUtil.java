package org.piaohao.fashionadmin.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.piaohao.fashionadmin.constant.ResultCode;
import org.piaohao.fashionadmin.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET = "JR&qUm@9";
    private static final String ISSUER = "FASHION_ADMIN";

    /**
     * 生成token
     *
     * @param claims
     * @return
     */
    public static String createToken(Map<String, String> claims, Integer expire) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            DateTime offset = DateUtil.offset(new DateTime(), DateField.SECOND, expire);
            JWTCreator.Builder builder = JWT.create()
                    .withIssuer(ISSUER)
                    .withExpiresAt(offset);
            claims.forEach(builder::withClaim);
            return builder.sign(algorithm);
        } catch (IllegalArgumentException e) {
            throw new Exception("生成token失败");
        }
    }

    /**
     * 验证jwt，并返回数据
     */
    public static Map<String, String> verifyToken(String token) {
        Algorithm algorithm;
        Map<String, Claim> map;
        try {
            algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            map = jwt.getClaims();
        } catch (Exception e) {
            if (e instanceof TokenExpiredException) {
                throw new ServiceException(ResultCode.AUTH_EXPIRE);
            }
            throw new ServiceException(ResultCode.AUTH_DENY);
        }
        Map<String, String> resultMap = new HashMap<>(map.size());
        map.forEach((k, v) -> resultMap.put(k, v.asString()));
        return resultMap;
    }

}