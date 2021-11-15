package com.antfact.twitter.service;

import com.antfact.twitter.bean.PostData;
import com.antfact.twitter.config.BlockingQueueBean;
import com.antfact.twitter.config.HmSyncHttpClientUtils;
import com.antfact.twitter.job.vpsLog;

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
