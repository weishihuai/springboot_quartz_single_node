package com.springboot.wsh.timers;

import org.quartz.*;
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

        //任务逻辑中获取触发器对象 有时候需要进行日志处理
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobKey jobKey = jobDetail.getKey();
        String jobName = jobKey.getName();
        String jobGroup = jobKey.getGroup();
        logger.info("分布式节点quartz-cluster-node-first，任务调度器中任务的名称：{}" + " / 组名称：{}", jobName, jobGroup);

        Trigger trigger = jobExecutionContext.getTrigger();
        TriggerKey triggerKey = trigger.getKey();
        String triggerName = triggerKey.getName();
        String triggerGroup = triggerKey.getGroup();
        logger.info("分布式节点quartz-cluster-node-first，任务调度器中触发器名称：{}" + " / 组名称：{}", triggerName, triggerGroup);

        //获取触发器中传递的一些参数
        JobDataMap triggerDataMap = trigger.getJobDataMap();
        Double triggerDoubleData = triggerDataMap.getDouble("triggerDoubleData");
        String triggerStringData = triggerDataMap.getString("triggerStringData");
        logger.info("分布式节点quartz-cluster-node-first，任务调度器中触发器中接受到的参数：{}  /  {}", triggerStringData, triggerDoubleData);

//        日志输出
//        分布式节点quartz-cluster-node-first，任务调度器中任务的名称：c8dc6a82-01c8-40b6-8704-fd4689225351组名称：com.springboot.wsh.timers.GoodSecKillRemindTimer
//        分布式节点quartz-cluster-node-first，任务调度器中触发器名称：c8dc6a82-01c8-40b6-8704-fd4689225351组名称：com.springboot.wsh.timers.GoodSecKillRemindTimer
//        分布式节点quartz-cluster-node-first，任务调度器中触发器中接受到的参数：Hello trigger! / 3.1415
    }
}
