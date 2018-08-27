package com.springboot.wsh.util;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishihuai
 * @Title JobUtil
 * @ProjectName springboot_quartz_single_node
 * @Description 任务创建工具类，方便后期维护任务
 * @date 2018/8/27 10:21
 */
public class JobUtil {

    private static final Logger logger = LoggerFactory.getLogger(JobUtil.class);

    @Autowired
    private Scheduler scheduler;

    private static JobUtil jobUtil;

    public JobUtil() {
        logger.info("init jobUtil");
        jobUtil = this;
    }

    public static JobUtil getInstance() {
        logger.info("return jobUtil");
        return JobUtil.jobUtil;
    }

    /**
     * 根据任务类名称、组名称、触发器规则创建任务
     *
     * @param jobClassName   任务类名称
     * @param jobGroup       组名称
     * @param cronExpression 触发器时间规则
     */
    public void addJob(Class<? extends Job> jobClassName, String jobGroup, String cronExpression) throws SchedulerException {
        //启动任务调度器
        scheduler.start();

        //创建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(jobClassName).withIdentity(jobClassName.getName(), jobGroup).build();
        //构建任务执行的时间规则
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        //创建触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName.getName(), jobGroup)
                .withSchedule(cronScheduleBuilder)
                .build();

        //将任务以及触发器绑定到任务调度器中
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
