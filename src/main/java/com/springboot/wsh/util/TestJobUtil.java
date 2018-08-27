package com.springboot.wsh.util;

import org.quartz.SchedulerException;

/**
 * @Title: TestJobUtil
 * @ProjectName springboot_quartz_single_node
 * @Description: 任务创建测试类
 * @Author Administrator
 * @Date 2018/8/27 10:31
 */
public class TestJobUtil {

    private static final String CRON_EXPRESSION = "0/5 * * * * ?";

    public static void main(String[] args) {
        try {
            JobUtil.getInstance().addJob(TestJob.class, TestJob.class.getName(), CRON_EXPRESSION);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
