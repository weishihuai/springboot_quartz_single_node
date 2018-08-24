package com.springboot.wsh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringbootQuartzSingleNodeApplication {
    private static Logger logger = LoggerFactory.getLogger(SpringbootQuartzSingleNodeApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringbootQuartzSingleNodeApplication.class, args);
        logger.info("【【【【【【定时任务分布式节点 - quartz-cluster-node-first 已启动】】】】】】");
    }
}
