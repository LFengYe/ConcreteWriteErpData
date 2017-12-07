/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.listener;

import com.cn.task.WriteERPData;
import com.cn.util.DatabaseOpt;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author LFeng
 */
public class StartInit implements ServletContextListener {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(StartInit.class);
    private static final int GPS_STATUS_SYNC_FREQUENCY = 10;
    
    //private ScheduledFuture future;
    private final ScheduledExecutorService timeOutScheduler = Executors.newSingleThreadScheduledExecutor();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            LOG.info("初始化开始...");
            //initData();
            /*
            //向定时任务线程池提交一个固定时间间隔执行的任务
            public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit);
            //向定时任务线程池提交一个固定延时间隔执行的任务
            public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit);
             */
            //30分钟执行一次
            timeOutScheduler.scheduleWithFixedDelay(new WriteERPData(DatabaseOpt.ORDER_ZZ), 0, GPS_STATUS_SYNC_FREQUENCY, TimeUnit.MINUTES);
            
            timeOutScheduler.scheduleWithFixedDelay(new WriteERPData(DatabaseOpt.ORDER_JB), 0, GPS_STATUS_SYNC_FREQUENCY, TimeUnit.MINUTES);
            
            timeOutScheduler.scheduleWithFixedDelay(new WriteERPData(DatabaseOpt.ORDER_RD), 0, GPS_STATUS_SYNC_FREQUENCY, TimeUnit.MINUTES);
            
            timeOutScheduler.scheduleWithFixedDelay(new WriteERPData(DatabaseOpt.ORDER_RF), 0, GPS_STATUS_SYNC_FREQUENCY, TimeUnit.MINUTES);
            
            timeOutScheduler.scheduleWithFixedDelay(new WriteERPData(DatabaseOpt.ORDER_RX), 0, GPS_STATUS_SYNC_FREQUENCY, TimeUnit.MINUTES);
            
            LOG.info("初始化完成...");
        } catch (Exception ex) {
            LOG.error("启动定时任务失败!", ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (timeOutScheduler != null)
            timeOutScheduler.shutdownNow();
        LOG.info("定时任务结束");
    }
}
