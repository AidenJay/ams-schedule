package com.fa.sesa.schedule.job.admin.service;


import com.fa.sesa.schedule.job.admin.core.model.XxlJobInfo;
import com.fa.sesa.schedule.job.core.biz.model.ReturnT;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * core job action for schedule-job
 *
 * @author xuxueli 2016-5-28 15:30:33
 */
public interface XxlJobService {

    /**
     * page list
     *
     * @param start
     * @param length
     * @param jobGroup
     * @param jobDesc
     * @param executorHandler
     * @param author
     * @return
     */
    public Map<String, Object> pageList(int start, int length, int jobGroup, int triggerStatus, String jobDesc,
                                        String executorHandler, String author);

    /**
     * add job
     *
     * @param jobInfo
     * @return
     */
    public ReturnT<String> add(XxlJobInfo jobInfo);

    /**
     * update job
     *
     * @param jobInfo
     * @return
     */
    public ReturnT<String> update(XxlJobInfo jobInfo);

    /**
     * remove job *
     *
     * @param id
     * @return
     */
    public ReturnT<String> remove(int id);

    /**
     * start job
     *
     * @param id
     * @return
     */
    public ReturnT<String> start(int id);

    /**
     * stop job
     *
     * @param id
     * @return
     */
    public ReturnT<String> stop(int id);

    /**
     * dashboard info
     *
     * @return
     */
    public Map<String, Object> dashboardInfo();

    /**
     * chart info
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public ReturnT<Map<String, Object>> chartInfo(Date startDate, Date endDate);

    ReturnT<String> addBatchJob(List<XxlJobInfo> jobInfos);

    /**
     * batch update job
     *
     * @param jobInfos
     * @return
     */
    public ReturnT<String> batchUpdate(List<XxlJobInfo> jobInfos);

    /**
     * batch remove job *
     *
     * @param batchId
     * @return
     */
    public ReturnT<String> batchRemove(Long batchId);

    /**
     * batch stop job *
     *
     * @param batchId
     * @return
     */
    public ReturnT<String> batchStop(Long batchId);

    /**
     * get job group id
     *
     * @param appName
     * @return
     */
    public ReturnT<String> getJobGroupIdByAppName(String appName);

    public List<XxlJobInfo> getJobsByBatchId(Long jobBatchId);

    public List<XxlJobInfo> getJobsByTaskId(String taskId);

    public ReturnT<String> updateNextTimeByTaskId(XxlJobInfo xxlJobInfo);

    public ReturnT<String> batchUpdateNextTimeByTaskId(List<XxlJobInfo> xxlJobInfos);

    public ReturnT<String> removeByBatchIdAndTaskId(Long jobBatchId, String taskId);

}
