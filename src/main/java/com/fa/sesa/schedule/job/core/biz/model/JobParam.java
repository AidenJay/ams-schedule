package com.fa.sesa.schedule.job.core.biz.model;

import java.io.Serializable;

/**
 * description:
 *
 * @author: johnson Wang
 * @date: 2021/1/4 14:49
 * @copyright: 2021, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
public class JobParam implements Serializable {
    private static final long serialVersionUID = 6692165506837493311L;

    private int id;				// 主键ID

    private Long jobBatchId;
    private int jobGroup;		// 执行器主键ID
    private String jobDesc;

    private String author;		// 负责人

    private String scheduleType;			// 调度类型
    private String scheduleConf;            // 调度配置，值含义取决于调度类型
    private String misfireStrategy;            // 调度过期策略

    private String executorRouteStrategy;    // 执行器路由策略
    private String executorHandler;            // 执行器，任务Handler名称
    private String executorParam;            // 执行器，任务参数
    private String executorBlockStrategy;    // 阻塞处理策略
    private int    executorTimeout;            // 任务执行超时时间，单位秒
    private int    executorFailRetryCount;		// 失败重试次数

    private String childJobId;		// 子任务ID，多个逗号分隔

    private int triggerStatus;		// 调度状态：0-停止，1-运行
    private long triggerLastTime;	// 上次调度时间
    private long triggerNextTime;	// 下次调度时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getJobBatchId() {
        return jobBatchId;
    }

    public void setJobBatchId(Long jobBatchId) {
        this.jobBatchId = jobBatchId;
    }

    public int getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(int jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getScheduleConf() {
        return scheduleConf;
    }

    public void setScheduleConf(String scheduleConf) {
        this.scheduleConf = scheduleConf;
    }

    public String getExecutorHandler() {
        return executorHandler;
    }

    public void setExecutorHandler(String executorHandler) {
        this.executorHandler = executorHandler;
    }

    public String getExecutorParam() {
        return executorParam;
    }

    public void setExecutorParam(String executorParam) {
        this.executorParam = executorParam;
    }

    public int getExecutorTimeout() {
        return executorTimeout;
    }

    public void setExecutorTimeout(int executorTimeout) {
        this.executorTimeout = executorTimeout;
    }

    public int getExecutorFailRetryCount() {
        return executorFailRetryCount;
    }

    public void setExecutorFailRetryCount(int executorFailRetryCount) {
        this.executorFailRetryCount = executorFailRetryCount;
    }

    public String getChildJobId() {
        return childJobId;
    }

    public void setChildJobId(String childJobId) {
        this.childJobId = childJobId;
    }

    public int getTriggerStatus() {
        return triggerStatus;
    }

    public void setTriggerStatus(int triggerStatus) {
        this.triggerStatus = triggerStatus;
    }

    public long getTriggerLastTime() {
        return triggerLastTime;
    }

    public void setTriggerLastTime(long triggerLastTime) {
        this.triggerLastTime = triggerLastTime;
    }

    public long getTriggerNextTime() {
        return triggerNextTime;
    }

    public void setTriggerNextTime(long triggerNextTime) {
        this.triggerNextTime = triggerNextTime;
    }

    public String getMisfireStrategy() {
        return misfireStrategy;
    }

    public void setMisfireStrategy(String misfireStrategy) {
        this.misfireStrategy = misfireStrategy;
    }

    public String getExecutorRouteStrategy() {
        return executorRouteStrategy;
    }

    public void setExecutorRouteStrategy(String executorRouteStrategy) {
        this.executorRouteStrategy = executorRouteStrategy;
    }

    public String getExecutorBlockStrategy() {
        return executorBlockStrategy;
    }

    public void setExecutorBlockStrategy(String executorBlockStrategy) {
        this.executorBlockStrategy = executorBlockStrategy;
    }
}