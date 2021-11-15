package com.antfact.transit.job;

import com.antfact.transit.bean.CompanyList;
import com.antfact.transit.service.GainService;
import com.antfact.transit.util.PropertiesUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class GainJob {
    @Autowired
    private GainService gainService;

    @Scheduled(fixedDelay = 100)  //间隔10毫秒
    public void first() throws InterruptedException {
        int gainThreadCount = Integer.parseInt(PropertiesUtil.getProperty("aginThreadCount"));
        for (int i = 0; i < gainThreadCount; i++) {
//            Thread gainThread=new GainThread();
//            gainThread.start();
            String companyLink = CompanyList.getAgent();
            gainService.postSend(companyLink);
            Thread.sleep(10);
        }
    }
}
