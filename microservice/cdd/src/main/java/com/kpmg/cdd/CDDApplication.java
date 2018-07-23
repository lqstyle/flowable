package com.kpmg.cdd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(
        exclude = {SecurityAutoConfiguration.class}
)
@MapperScan("com.kpmg.cdd.repository")
@EnableSwagger2
@ComponentScan(basePackages = {"com.kpmg.cdd", "org.flowable.ui"})
@EnableTransactionManagement
public class CDDApplication {
    public static ConfigurableApplicationContext AC;
    public static void main(String[] args) {
        CDDApplication.AC=SpringApplication.run(CDDApplication.class, args);
    }
}
