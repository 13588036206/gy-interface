package com.gy.gyweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages ={"com.gy"})
@MapperScan(value = "com.gy.dao.mapper")
@ServletComponentScan(basePackages = "com.gy.gyweb.filter")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GyWebApplication extends SpringBootServletInitializer {
    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(GyWebApplication.class);

    }

    public static void main(String[] args) {
        SpringApplication.run(GyWebApplication.class, args);
    }

}
