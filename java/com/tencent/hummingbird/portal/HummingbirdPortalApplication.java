package com.tencent.hummingbird.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: 玛卡巴卡萌♡
 * Date: 2023-04-09
 * Time: 10:20
 */
@SpringBootApplication
@MapperScan("com.tencent.hummingbird.portal.mapper")

public class HummingbirdPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(HummingbirdPortalApplication.class,args);
    }
}
