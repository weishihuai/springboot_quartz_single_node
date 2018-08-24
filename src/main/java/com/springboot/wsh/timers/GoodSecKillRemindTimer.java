package com.springboot.wsh.timers;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 商品秒杀定时任务
 * 传递参数到任务中进行逻辑处理
 */
public class GoodSecKillRemindTimer extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(GoodSecKillRemindTimer.class);

    /**
     * 定时任务逻辑实现
     *
     * @param jobExecutionContext 任务执行上下文对象
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //传递参数到任务中
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Long goodId = jobDataMap.getLong("goodId");
        logger.info("分布式节点quartz-cluster-node-first，开始处理秒杀商品：{}，关注用户推送消息.", goodId);
    }
}
