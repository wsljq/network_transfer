package com.antfact.twitter.service;

import com.antfact.twitter.bean.PostData;
import com.antfact.twitter.config.BlockingQueueBean;
import com.antfact.twitter.config.HmSyncHttpClientUtils;
import com.antfact.twitter.job.Statistics;

import org.apache.http.Consts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class GainService {
    @Async("poolExecutor")
    public void postSend(String requestUrl) {
        List<PostData> postDataList =  BlockingQueueBean.getQueue();
        if (postDataList == null) {
            return;
        }
        for (PostData json : postDataList) {
            MultipartEntityBuilder mutipart = MultipartEntityBuilder.create();
            mutipart.addPart("data", new StringBody(json.getData(), ContentType.create("text/plain", Consts.UTF_8)));
            mutipart.addBinaryBody("source", json.getSource());
            HmSyncHttpClientUtils.httpPost(requestUrl, mutipart);
            Statistics.add(Long.valueOf(1));
        }
    }
}
