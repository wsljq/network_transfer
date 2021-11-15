package com.antfact.transit.service;

import com.antfact.transit.bean.PostData;
import com.antfact.transit.config.BlockingQueueBean;
import com.antfact.transit.config.HmSyncHttpClientUtils;
import com.antfact.transit.job.vpsLog;

import org.apache.http.client.methods.HttpPost;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class VpsService {
    @Async("poolExecutor")
    public void PostSend(String requestUrl) {
        HttpPost httpPost = new HttpPost(requestUrl);
        List<PostData> postDataList = HmSyncHttpClientUtils.httpPost(httpPost, requestUrl);
        if (postDataList == null) {
            return;
        }
        BlockingQueueBean.setQueue(postDataList);
        vpsLog.add(Long.valueOf(1));
    }
}
