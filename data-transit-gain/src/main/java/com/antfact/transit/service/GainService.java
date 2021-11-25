package com.antfact.transit.service;

import com.antfact.transit.bean.PostData;
import com.antfact.transit.config.BlockingQueueBean;
import com.antfact.transit.config.HmSyncHttpClientUtils;
import com.antfact.transit.job.Statistics;

import org.apache.http.Consts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class GainService {

    @Value("${isAginThreadPost}")
    private Boolean isAginThreadPost;

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
            if(isAginThreadPost){
                HmSyncHttpClientUtils.httpPost(requestUrl, mutipart);
            }
            Statistics.add(Long.valueOf(1));
        }
    }
}
