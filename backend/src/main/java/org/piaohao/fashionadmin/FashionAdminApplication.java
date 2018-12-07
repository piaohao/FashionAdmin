package org.piaohao.fashionadmin;

import cn.hutool.core.lang.Dict;
import org.mybatis.spring.annotation.MapperScan;
import org.piaohao.fashionadmin.annotation.ClearAuth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@MapperScan("org.piaohao.fashionadmin.db.*.mapper")
public class FashionAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(FashionAdminApplication.class, args);
    }

    @RestController
    public static class IndexController {
        @RequestMapping("")
        @ClearAuth
        public Object index() {
            return new Dict().set("name", "piaohao").set("age", null);
        }
    }
}
