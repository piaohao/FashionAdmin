package org.piaohao.fashionadmin.module.system.account;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.piaohao.fashionadmin.annotation.ClearAuth;
import org.piaohao.fashionadmin.constant.ResultCode;
import org.piaohao.fashionadmin.db.system.entity.User;
import org.piaohao.fashionadmin.db.system.mapper.UserMapper;
import org.piaohao.fashionadmin.db.system.service.IUserService;
import org.piaohao.fashionadmin.exception.ServiceException;
import org.piaohao.fashionadmin.model.PageReq;
import org.piaohao.fashionadmin.model.TableData;
import org.piaohao.fashionadmin.module.system.account.model.DeleteUserReq;
import org.piaohao.fashionadmin.module.system.account.model.LoginReq;
import org.piaohao.fashionadmin.util.JwtUtil;
import org.piaohao.fashionadmin.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class AccountController {

    @Autowired
    private IUserService userService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/api/login/account")
    @ClearAuth
    public Object login(@RequestBody LoginReq loginReq) {
        String userName = loginReq.getUserName();
        String password = loginReq.getPassword();
        String type = loginReq.getType();
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(password)) {
            throw new ServiceException(ResultCode.AUTH_QUERY_NOT_VALID);
        }
        User user = userService.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserName, userName)
                .eq(User::getPassword, UserUtil.encryptPassword(password)));
        if (user == null) {
            throw new ServiceException(ResultCode.AUTH_FAILED);
        }
        String currentAuthority = "admin";
        Map<String, String> claims = MapUtil.<String, String>builder()
                .put("userId", user.getId() + "")
                .put("userName", userName)
                .build();
        try {
            String token = JwtUtil.createToken(claims, 600);
            return new Dict()
                    .set("status", "ok")
                    .set("type", type)
                    .set("token", token)
                    .set("currentAuthority", currentAuthority);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.AUTH_TOKEN_GENERATE_FAILED);
        }
    }

    @GetMapping("/api/currentUser")
    public Object currentUser(HttpServletRequest request) {
        Integer userId = Convert.toInt(request.getAttribute("userId"));
        User user = userService.getById(userId);
        if (user == null) {
            throw new ServiceException(ResultCode.USER_NOT_EXISTS);
        }
        return user;
    }

    @GetMapping("/api/currentUser2")
    public Object currentUser2() {
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

    @PostMapping("/api/users")
    public Object users(@RequestBody PageReq pageReq) {
        pageReq.check();
        IPage<User> page = new Page<>(pageReq.getCurrentPage(), pageReq.getPageSize());
        userMapper.selectPage(page, new LambdaQueryWrapper<User>().eq(User::getStatus, 1));
        return TableData.fromPage(page);
    }

    @PostMapping("/api/saveUser")
    public Object saveUser(@RequestBody User user) {
        Integer id = user.getId();
        String userName = user.getUserName();
        if (id == null) {
            User record = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserName, userName));
            if (record != null) {
                throw new ServiceException(ResultCode.USER_NAME_CONFLICT);
            }
            user.setPassword(UserUtil.encryptPassword(user.getPassword()));
            user.setCreatedTime(System.currentTimeMillis());
            user.setUpdateTime(System.currentTimeMillis());
            userService.save(user);
        } else {
            User record = userService.getById(id);
            if (record == null) {
                throw new ServiceException(ResultCode.USER_NOT_EXISTS);
            }
            record.setNickName(user.getNickName());
            record.setPassword(UserUtil.encryptPassword(user.getPassword()));
            record.setUpdateTime(System.currentTimeMillis());
            userService.updateById(record);
        }
        return user;
    }

    @PostMapping("/api/removeUser")
    public Object removeUser(@RequestBody DeleteUserReq req) {
        for (User user : req.getData()) {
            User record = userService.getById(user.getId());
            if (record == null) {
                throw new ServiceException(ResultCode.USER_NOT_EXISTS);
            }
            record.setStatus(0);
            userService.updateById(record);
        }

        return "";
    }

}
