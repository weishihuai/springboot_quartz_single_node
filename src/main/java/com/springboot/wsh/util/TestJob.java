package com.springboot.wsh.util;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @Title: TestJob
 * @ProjectName springboot_quartz_single_node
 * @Description: 测试任务
 * @Author Administrator
 * @Date 2018/8/27 10:35
 */
public class TestJob extends QuartzJobBean {

    private static Logger logger = LoggerFactory.getLogger(TestJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("测试任务创建");
    }
}
