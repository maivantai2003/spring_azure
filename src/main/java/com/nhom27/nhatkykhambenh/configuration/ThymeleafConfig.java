package com.nhom27.nhatkykhambenh.configuration;

import com.nhom27.nhatkykhambenh.utils.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafConfig {
    @Bean
    public StringUtils stringUtils() {
        return new StringUtils();
    }
}

