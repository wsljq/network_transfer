package com.antfact.transit.service;

import com.antfact.transit.config.HmSyncHttpClientUtils;

import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author:linjunqing (linjunqing @ civiw.com)
 * Date:2021/11/24 16:45
 * Description: 测试传输的效率
 * Copyright (c) 2010-2021 EEFUNG Software Co.Ltd. All rights reserved.
 * 版权所有(c)2010-2021 湖南蚁为软件有限公司。保留所有权利。
 */
@Slf4j
@Component
public class TestService {

    @Value("${testVpsUrl}")
    private String testVpsUrl;

    @Value("${intervalTimeThread}")
    private Long intervalTimeThread;

    public static boolean flag = true;

    public static Date endTime;

    /**
     * 与服务端进行数据测试
     */
    public void testVPSVelocity(Integer ckSzie, Integer thread, String endTime) {
        flag = true;
        String url=testVpsUrl+"?ckSzie="+ckSzie;
        this.endTime = toDate(endTime);
        Timer timer = new Timer();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(thread);

        for(int i=0;i<ckSzie;i++){
            Runnable syncRunnable = new Runnable() {
                @Override
                public void run() {
                    final HttpPost httpPost = new HttpPost(url);
                    HmSyncHttpClientUtils.httpPost( url,httpPost);
//                    log.info(Thread.currentThread().getName());
                }
            };
            executorService.scheduleWithFixedDelay(syncRunnable, 10, intervalTimeThread, TimeUnit.MILLISECONDS);
        }

        timer.schedule(new TimerTask() {//定时监测是否到期
            public void run() {
                if (new Date().after(TestService.endTime)) {
                    flag = false;
                }
                if (flag = false) {
                    executorService.shutdownNow();
                    timer.cancel();
                }
            }
        }, 0, intervalTimeThread);//延迟 间隔多久执行一次


    }



    public void testVPSStop(){
        flag=false;
    }

    /**
     * 常规自动日期格式识别  返回对应的时间格式  yyyy-MM-dd   yyyy-MM-dd HH:mm:ss
     *
     * @param str 时间字符串
     * @return Date
     * @author dc
     */
    private static String getDateFormat(String str) {//lan120567
        boolean year = false;
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if (pattern.matcher(str.substring(0, 4)).matches()) {
            year = true;
        }
        StringBuilder sb = new StringBuilder();
        int index = 0;
        if (!year) {
            if (str.contains("月") || str.contains("-") || str.contains("/")) {
                if (Character.isDigit(str.charAt(0))) {
                    index = 1;
                }
            } else {
                index = 3;
            }
        }
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if (Character.isDigit(chr)) {
                if (index == 0) {
                    sb.append("y");
                }
                if (index == 1) {
                    sb.append("M");
                }
                if (index == 2) {
                    sb.append("d");
                }
                if (index == 3) {
                    sb.append("H");
                }
                if (index == 4) {
                    sb.append("m");
                }
                if (index == 5) {
                    sb.append("s");
                }

                if (index == 6) {
                    sb.append("S");
                }

            } else {
                if (i > 0) {
                    char lastChar = str.charAt(i - 1);
                    if (Character.isDigit(lastChar)) {
                        index++;
                    }
                }
                sb.append(chr);
            }
        }
        return sb.toString();
    }

    /**
     * @description: 转成Date
     * @author: stuil
     */
    public static Date toDate(String strDate) {
        // 先根据时间字符串的格式 转成Date
        try {
            return new SimpleDateFormat(getDateFormat(strDate)).parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
