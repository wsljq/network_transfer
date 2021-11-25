package com.antfact.transit.controller;

import com.antfact.transit.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author:linjunqing (linjunqing @ civiw.com)
 * Date:2021/11/24 15:56
 * Description:
 * Copyright (c) 2010-2021 EEFUNG Software Co.Ltd. All rights reserved.
 * 版权所有(c)2010-2021 湖南蚁为软件有限公司。保留所有权利。
 */
@Controller
@Slf4j
public class indexController {
    @Autowired
    private TestService testService;

    /**
     * 发起请求去服务端获的请求
     * @param ckSzie 每个数据包大小
     * @param thread 多少个线程
     * @param endTime 运行结束的时间
     */
    @RequestMapping("/ants/ant/test")
    @ResponseBody
    public void vps(Integer ckSzie, Integer thread,String endTime) {
        testService.testVPSVelocity(ckSzie, thread, endTime);
    }

    /**
     * 停止发起请求去服务端获的请求
     */
    @RequestMapping("/ants/ant/test/stop")
    @ResponseBody
    public void stop() {
        testService.testVPSStop();
    }

    /**
     * 国内访问此方法，返回所要大小的数据包
     * @param ckSzie 数据包大小
     * @param response
     */
    @RequestMapping("/ants/ant/test/postdata")
    @ResponseBody
    public void speed(Integer ckSzie,HttpServletResponse response) {
        ByteArrayInputStream stream = null;
//        response.setContentType("image/gif");
        StringBuilder sb1=new StringBuilder();
        for(int i=0;i<1000*1000*ckSzie;i++){
            sb1.append('a'+"");
        }
        String str=sb1.toString();
        try {
            OutputStream out = response.getOutputStream();
            stream = new ByteArrayInputStream(str.getBytes());
            byte[] b = new byte[stream.available()];
            stream.read(b);
            out.write(b);
            out.flush();
        } catch (IOException e) {
            log.error("IO流异常", e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    log.error("IO流关闭异常", e);
                }
            }
        }
    }
}
