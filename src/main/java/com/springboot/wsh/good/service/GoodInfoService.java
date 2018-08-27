package com.springboot.wsh.good.service;

import com.springboot.wsh.good.entity.GoodInfoEntity;
import com.springboot.wsh.good.jpa.GoodInfoRepository;
import com.springboot.wsh.timers.GoodAddTimer;
import com.springboot.wsh.timers.GoodSecKillRemindTimer;
import com.springboot.wsh.timers.GoodStockCheckTimer;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;


@Service
@Transactional(rollbackFor = Exception.class)
public class GoodInfoService {
    /**
     * 注入任务调度器
     */
    @Autowired
    private Scheduler scheduler;
    /**
     * 商品数据接口
     */
    @Autowired
    private GoodInfoRepository goodInfoRepository;

    /**
     * 保存商品基本信息
     *
     * @param good 商品实例
     * @return
     */
    public Long saveGood(GoodInfoEntity good) throws Exception {
        goodInfoRepository.save(good);
        //构建创建商品定时任务
        buildCreateGoodTimer();
        //构建商品库存定时任务
        buildGoodStockTimer();
        //商品秒杀定时任务
        buildGoodSecKillRemindTimer(good.getId());
        return good.getId();
    }

    /**
     * 构建创建商品定时任务
     */
    private void buildCreateGoodTimer() throws Exception {
        //设置开始时间为1分钟后
        long startAtTime = System.currentTimeMillis() + 1000 * 60;
        //任务名称
        String name = UUID.randomUUID().toString();
        //任务所属分组
        String group = GoodAddTimer.class.getName();
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(GoodAddTimer.class).withIdentity(name, group).build();
        //创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group).startAt(new Date(startAtTime)).build();
        //将触发器与任务绑定到调度器内
        scheduler.scheduleJob(jobDetail, trigger);
    }

    private void buildGoodSecKillRemindTimer(Long goodId) throws Exception {
        long startTime = System.currentTimeMillis() + 1000 * 60;
        String name = UUID.randomUUID().toString();
        String group = GoodSecKillRemindTimer.class.getName();
        JobDetail jobDetail = JobBuilder.newJob(GoodSecKillRemindTimer.class).withIdentity(name, group).build();
        jobDetail.getJobDataMap().put("goodId", goodId);

        //链式写法
        //触发器传递参数  usingJobData支持键值对的值
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                .usingJobData("triggerStringData", "Hello trigger!")
                .usingJobData("triggerDoubleData", 3.1415D)
                .startAt(new Date(startTime))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 构建商品库存定时任务
     *
     * @throws Exception
     */
    private void buildGoodStockTimer() throws Exception {
        //任务名称
        String name = UUID.randomUUID().toString();
        //任务所属分组
        String group = GoodStockCheckTimer.class.getName();

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/30 * * * * ?");
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(GoodStockCheckTimer.class).withIdentity(name, group).build();
        //创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                .withSchedule(scheduleBuilder)
                .build();
        //将触发器与任务绑定到调度器内
        scheduler.scheduleJob(jobDetail, trigger);
    }

}
