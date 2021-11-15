package com.antfact.transit.config;

import com.antfact.transit.util.PropertiesUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


@Configuration
public class PropertiesConfig {
    @Resource
    private Environment env;
    @PostConstruct
    public void setProperties(){
        PropertiesUtil.setEnvironment(env);
    }
}
