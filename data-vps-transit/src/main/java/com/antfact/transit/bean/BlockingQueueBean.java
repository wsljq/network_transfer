package com.antfact.transit.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


@Configuration
public class BlockingQueueBean {

    @Value("${queueSize}")
    private int queueSize;

    @Bean
    public BlockingQueue queue(){
        BlockingQueue<TwitterPostData> queue=new ArrayBlockingQueue<TwitterPostData>(queueSize);
        return queue;
    }
}
