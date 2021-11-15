package com.antfact.transit.job;

import com.antfact.transit.bean.VpsList;
import com.antfact.transit.service.VpsService;
import com.antfact.transit.util.PropertiesUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class VpsJob {
    @Autowired
    private VpsService vpsService;

    @Scheduled(fixedDelay = 100)  //间隔10毫秒
    public void first() {
        int VPSThreadCount= Integer.parseInt(PropertiesUtil.getProperty("VPSThreadCount"));
        for(int i=0;i<VPSThreadCount;i++){
            String companyLink = VpsList.getVps();
            vpsService.PostSend(companyLink);
        }
    }
}
