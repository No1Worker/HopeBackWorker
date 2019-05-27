package com.hope.hopeback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.hope.hopeback.dao")
@EnableCaching
public class HopebackApplication {

    public static void main(String[] args) {
        SpringApplication.run(HopebackApplication.class, args);
    }

}
