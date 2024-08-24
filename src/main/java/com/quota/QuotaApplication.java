package com.quota;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.quota.dal.dao")
public class QuotaApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotaApplication.class, args);
    }

}
