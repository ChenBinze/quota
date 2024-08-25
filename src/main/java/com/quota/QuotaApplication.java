package com.quota;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.quota.dal.mapper")
public class QuotaApplication  {

    public static void main(String[] args) {
        SpringApplication.run(QuotaApplication.class, args);
    }
}
