package com.antfact.twitter.service;

import com.antfact.twitter.bean.TwitterPostData;
import com.antfact.twitter.job.Statistics;
import com.antfact.twitter.util.GodSerializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;


@Component
@Slf4j
public class TransitService {
    @Autowired
    private BlockingQueue queue;

    @Value("${queueTimeOut}")
    private long timeOut;//设置超时时间

//    @Async("poolExecutor")
    @Async
    public void setQueue(String data,String source){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("data",data);
        jsonObject.put("source",source);
        String str= JSON.toJSONString(jsonObject);
        try {
            boolean flag=queue.offer(str,timeOut, TimeUnit.MILLISECONDS);//10秒
            if(!flag){
                log.error("存放队列失败，队列长度为："+queue.size());
            }
        } catch (InterruptedException e) {
            log.error("存放队列失败",e);
        }
    }
    @Async
    public void setQueue(TwitterPostData twitterPostData){
        boolean flag=queue.offer(twitterPostData);//3秒
        if(!flag){
            log.warn("存放队列失败，队列长度为："+queue.size());
        }
//        try {
//            boolean flag=queue.offer(twitterPostData,timeOut, TimeUnit.MILLISECONDS);//3秒
//            if(!flag){
//                log.info("存放队列失败，队列长度为："+queue.size());
//            }
//        } catch (InterruptedException e) {
//            log.error("存放队列失败",e);
//        }
    }
    @Async
    public void setQueue(Map map){
        try {
            boolean flag=queue.offer(map,timeOut, TimeUnit.MILLISECONDS);//3秒
            if(!flag){
                log.error("存放队列失败，队列长度为："+queue.size());
            }
        } catch (InterruptedException e) {
            log.error("存放队列失败",e);
        }
    }


    @Async
    public Future<String> getQueue(HttpServletResponse response){
        response.setContentType("application/octet-stream");
        List<TwitterPostData> list=new ArrayList<>();
        byte [] bytes=null;
        try {
            for (int i = 0; i < 100; i++) {
                TwitterPostData twitterPostData = (TwitterPostData) queue.poll(timeOut, TimeUnit.MILLISECONDS);
                if (twitterPostData != null) {
                    Statistics.add(1);
                    list.add(twitterPostData);
                }
            }
            bytes= GodSerializer.serialize(list);
            OutputStream out = response.getOutputStream();
            out.write(bytes);
            out.flush();
        } catch (InterruptedException | IOException e) {
            log.error("获取中断异常", e);
        }
        return new AsyncResult("ok");
    }

}
