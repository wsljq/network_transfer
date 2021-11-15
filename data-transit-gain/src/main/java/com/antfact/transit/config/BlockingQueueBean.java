package com.antfact.transit.config;

import com.antfact.transit.bean.PostData;
import com.antfact.transit.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


@Slf4j
public class BlockingQueueBean {
    public static BlockingQueue<List<PostData>> queue=null;

    static {
        int queueSize = Integer.parseInt(PropertiesUtil.getProperty("queueSize"));
        queue=new ArrayBlockingQueue<List<PostData> >(queueSize);
    }

    public static void setQueue(List<PostData>  data){
//        long timeOut=Long.parseLong(PropertiesUtil.getProperty("queueTimeOut"));//设置超时时间
        boolean flag=queue.offer(data);//3秒
        if(!flag){
            log.error("存放队列失败，队列长度为："+queue.size());
        }
//        try {
//            boolean flag=queue.offer(data,timeOut, TimeUnit.MILLISECONDS);//3秒
//            if(!flag){
//                log.error("存放队列失败，队列长度为："+queue.size());
//            }
//        } catch (InterruptedException e) {
//            log.error("存放队列失败",e);
//        }
    }
    public static List<PostData>  getQueue(){
        List<PostData>  json=null;
//        long timeOut=Long.parseLong(PropertiesUtil.getProperty("queueTimeOut"));//设置超时时间
        json= queue.poll();
//        try {
//        } catch (InterruptedException e) {
//            log.error("获取队列内容发生中断异常",e);
//        }
        return json;
    }
}
