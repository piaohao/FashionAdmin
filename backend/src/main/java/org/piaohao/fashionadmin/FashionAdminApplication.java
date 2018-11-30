package org.piaohao.fashionadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.piaohao.fashionadmin.db.*.mapper")
public class FashionAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(FashionAdminApplication.class, args);
    }
}
