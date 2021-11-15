package com.antfact.transit.job;

import com.antfact.transit.config.BlockingQueueBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class LogJob {
    private static final Log log = LogFactory.getLog(LogJob.class);

    @Scheduled(cron = "0/20 * * * * ?")
    public void queueSize() {
        long count= BlockingQueueBean.queue.size();
        log.info("当前队列大小："+count);
    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void execute() {
        long count=Statistics.setConunt(0l);
        log.info("发往公司20秒内:"+count+",预计一小时:"+count*3*60+",预计每天:"+count*3*60*24);
    }
    @Scheduled(cron = "0 0/1 * * * ?")
    public void sumDay() {
        long count=Statistics.getDayConunt(0l);
        log.info("发往公司一分钟内:"+count+",预计一天下来有"+count*60*24);
    }
    @Scheduled(cron = "0/20 * * * * ?")
    public void vpsexecute() {
        long count=vpsLog.setConunt(0l);
        log.info("获取境外20秒内:"+count+",预计一小时:"+count*3*60+",预计每天:"+count*3*60*24);
    }
    @Scheduled(cron = "0 0/1 * * * ?")
    public void vpssumDay() {
        long count=vpsLog.getDayConunt(0l);
        log.info("获取境外一分钟内:"+count+",预计每天"+count*60*24);
    }
}
