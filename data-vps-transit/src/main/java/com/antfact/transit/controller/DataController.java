package com.antfact.transit.controller;

import com.antfact.transit.bean.TwitterPostData;
import com.antfact.transit.job.Statistics;
import com.antfact.transit.job.controllerLog;
import com.antfact.transit.job.vpsLog;
import com.antfact.transit.service.TransitService;
import com.antfact.transit.util.GodSerializer;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class DataController {
    @Autowired
    private BlockingQueue queue;
    @Autowired
    private TransitService transitService;
    @Value("${queueTimeOut}")
    private long timeOut;//设置超时时间

    @Value("${queueSize}")
    private int queueSize;//设置超时时间

    /**
     * 境外vps访问此方法，接收数据放入队列里
     */
    @RequestMapping("/ants/ant/data/postdata")
    @ResponseBody
    public void transit(HttpServletRequest request, @RequestPart(name = "data", required = false) String taskInfo,
                        @RequestPart(name = "source", required = false) byte[] sourceCodeBin
                       ) {
        try {
            TwitterPostData twitterPostData = new TwitterPostData(sourceCodeBin, taskInfo);
            transitService.setQueue(twitterPostData);
            vpsLog.add(1);
        } catch (Throwable e) {
            log.error("接收异常", e);
        }
    }
    @RequestMapping("/index")
    @ResponseBody
    public String transit(ServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = WebUtils.getParametersStartingWith(request, "");
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if( entry.getValue() instanceof String){
                System.out.println(entry.getKey()+"--->"+entry.getValue());
            } else if( entry.getValue() instanceof Byte){
                System.out.println(entry.getKey()+"---> 是byte类型");
            }
        }
//        if (queue.size() == queueSize) {
//            response.setStatus(200);
//            log.info("队列已满");
//            return "no";
//        }
//        transitService.setQueue(map);
//        vpsLog.add(1);
        return "ok";
    }

//
//        catch (ServletException e) {
//            log.error("接收错误",e);
//        }
//        //从request取出file的方法
//        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
//        String data=multipartRequest.getParameter("data");
//
//        MultipartFile file = multipartRequest.getFile("source");
//        try {
//            byte[] bytes=file.getBytes();
//            transitService.setQueue(data,new String(bytes));
//            Statistics.add(1);
//        } catch (IOException e) {
//            log.error("接收错误",e);
//        }


//    public void transit(@RequestParam(required = false) String data,@RequestParam(required = false) String source){
//        transitService.setQueue(data,source);
//    }

    /**
     * 国内公司访问此方法，接收数据放入队列里
     */
    @RequestMapping("/ants/ant/get/postdata")
    @ResponseBody
    public void transit(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        List<TwitterPostData> list = new ArrayList<>();
        try {
            for (int i = 0; i < 50; i++) {
                TwitterPostData twitterPostData = (TwitterPostData) queue.poll();
                if (twitterPostData != null) {
                    Statistics.add(1);
                    list.add(twitterPostData);
                } else {
//                    log.info("取数据未到最大值，实际：" + (i + 1));
                    controllerLog.add((i + 1));
                    break;
                }
            }
            if(list.size()==50) controllerLog.add();
            byte[] bytes = GodSerializer.serialize(list);
            OutputStream out = response.getOutputStream();
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            log.error("获取中断异常", e);
        }
    }

    /**
     * 国内公司访问此方法，接收数据放入队列里
     */
    @RequestMapping("/ants/ant/stream/postdata")
    @ResponseBody
    public void stream(HttpServletResponse response) {
        ByteArrayInputStream stream = null;
        response.setContentType("image/gif");
        try {
            OutputStream out = response.getOutputStream();
            Map map = (Map) queue.poll(timeOut, TimeUnit.MILLISECONDS);
            if (map != null) {
                Statistics.add(1);
                JSONObject jsonObject = new JSONObject(map);
                stream = new ByteArrayInputStream(jsonObject.toJSONString().getBytes());
                byte[] b = new byte[stream.available()];
                stream.read(b);
                out.write(b);
                out.flush();
            } else {
                response.setStatus(400);
                return;
            }
        } catch (InterruptedException e) {
            log.error("获取中断异常", e);
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
