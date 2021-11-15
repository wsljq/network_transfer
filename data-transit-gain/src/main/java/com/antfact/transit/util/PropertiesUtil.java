package com.antfact.transit.util;

import org.springframework.core.env.Environment;

public class PropertiesUtil {
    private static Environment env=null;

    public static void setEnvironment(Environment environment){
        env=environment;
    }
    public static String getProperty(String key){
        return  PropertiesUtil.env.getProperty(key);
    }
}
