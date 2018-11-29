package org.piaohao.fashionadmin.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class JwtUtilTest {

    @Test
    public void createToken() {
        Map<String, String> claims = MapUtil.<String, String>builder()
                .put("id", "111111")
                .put("name", "piaohao")
                .build();
        try {
            String token = JwtUtil.createToken(claims, 20);
            System.out.println(token);
            Map<String, String> map = JwtUtil.verifyToken(token);
            System.out.println(JSONUtil.toJsonPrettyStr(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void verifyToken() {
        try {
            Map<String, String> map = JwtUtil.verifyToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJGQVNISU9OX0FETUlOIiwibmFtZSI6InBpYW9oYW8iLCJpZCI6IjExMTExMSIsImV4cCI6MTU0MzM5MjUwNn0.Ks0M3n1dvPUnVnzYWFH3KKXFEkDuG8JoUSUuox71dC0");
            System.out.println(JSONUtil.toJsonPrettyStr(map));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}