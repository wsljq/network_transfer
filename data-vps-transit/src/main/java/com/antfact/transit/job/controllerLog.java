package com.antfact.transit.job;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class controllerLog {
    private static Map<String, Integer> map = new HashMap<String, Integer>();
    private static String CONUNT_0_10 = "0~10";
    private static String CONUNT_11_30 = "11~30";
    private static String CONUNT_30_49 = "31~50";
    private static int amount = 0;//统计总的请求

    public static void add(long addCount) {
        String key = null;
        if (addCount <= 10) {
            key = CONUNT_0_10;
        }else if (addCount <= 30) {
            key = CONUNT_11_30;
        }else if (addCount <= 50) {
            key = CONUNT_30_49;
        }
        synchronized (controllerLog.class) {
            if (map.containsKey(key)) {
                int count=map.get(key);
                map.put(key, ( count+ 1));
            } else {
                map.put(key, 1);
            }
        }
        amount++;
    }
    public static void add() {
        amount++;
    }

    public static void init() {
        synchronized (controllerLog.class) {
            log.info("总共："+amount+"个请求");
            for (Map.Entry<String, Integer> set:map.entrySet()) {
                log.info(set.getKey()+"："+set.getValue());
            }
            amount=0;
            map = new HashMap<String, Integer>();
        }
    }

}