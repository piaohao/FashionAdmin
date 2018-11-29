package org.piaohao.fashionadmin.account;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import org.piaohao.fashionadmin.account.model.LoginReq;
import org.piaohao.fashionadmin.annotation.ClearAuth;
import org.piaohao.fashionadmin.constant.ResultCode;
import org.piaohao.fashionadmin.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AccountController {

    @PostMapping("/api/login/account")
    @ClearAuth
    public Object login(@RequestBody LoginReq loginReq) {
        String userName = loginReq.getUserName();
        String password = loginReq.getPassword();
        String type = loginReq.getType();
        if (password.equals("ant.design")) {
            String currentAuthority = null;
            if (userName.equals("admin")) {
                currentAuthority = "admin";
            } else if (userName.equals("user")) {
                currentAuthority = "user";
            }
            Map<String, String> claims = MapUtil.<String, String>builder()
                    .put("id", "111111")
                    .put("name", userName)
                    .build();
            try {
                String token = JwtUtil.createToken(claims, 600);
                return new Dict()
                        .set("status", "ok")
                        .set("type", type)
                        .set("token", token)
                        .set("currentAuthority", currentAuthority);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Dict()
                .set("status", "error")
                .set("type", type)
                .set("currentAuthority", "guest");
    }

    @GetMapping("/api/currentUser")
    public Object currentUser() {
        String str = "{\n" +
                "    \"name\": \"Serati Ma\",\n" +
                "    \"avatar\": \"https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png\",\n" +
                "    \"userid\": \"00000001\",\n" +
                "    \"email\": \"antdesign@alipay.com\",\n" +
                "    \"signature\": \"海纳百川，有容乃大\",\n" +
                "    \"title\": \"交互专家\",\n" +
                "    \"group\": \"蚂蚁金服－某某某事业群－某某平台部－某某技术部－UED\",\n" +
                "    \"tags\": [\n" +
                "        {\n" +
                "            \"key\": \"0\",\n" +
                "            \"label\": \"很有想法的\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"key\": \"1\",\n" +
                "            \"label\": \"专注设计\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"key\": \"2\",\n" +
                "            \"label\": \"辣~\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"key\": \"3\",\n" +
                "            \"label\": \"大长腿\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"key\": \"4\",\n" +
                "            \"label\": \"川妹子\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"key\": \"5\",\n" +
                "            \"label\": \"海纳百川\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"notifyCount\": 12,\n" +
                "    \"country\": \"China\",\n" +
                "    \"geographic\": {\n" +
                "        \"province\": {\n" +
                "            \"label\": \"浙江省\",\n" +
                "            \"key\": \"330000\"\n" +
                "        },\n" +
                "        \"city\": {\n" +
                "            \"label\": \"杭州市\",\n" +
                "            \"key\": \"330100\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"address\": \"西湖区工专路 77 号\",\n" +
                "    \"phone\": \"0752-268888888\"\n" +
                "}";
        return JSONUtil.parseObj(str);
    }

    @RequestMapping("/test")
    @ResponseBody
    public Object json() {
        return ResultCode.DEFAULT_ERROR;
    }

}
