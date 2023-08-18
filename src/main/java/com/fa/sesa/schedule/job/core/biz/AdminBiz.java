package com.fa.sesa.schedule.job.core.biz;

import com.fa.sesa.schedule.job.core.biz.model.HandleCallbackParam;
import com.fa.sesa.schedule.job.core.biz.model.JobParam;
import com.fa.sesa.schedule.job.core.biz.model.RegistryParam;
import com.fa.sesa.schedule.job.core.biz.model.ReturnT;

import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:52:49
 */
public interface AdminBiz {


    // ---------------------- callback ----------------------

    /**
     * callback
     *
     * @param callbackParamList
     *
     * @return
     */
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList);


    // ---------------------- registry ----------------------

    /**
     * registry
     *
     * @param registryParam
     *
     * @return
     */
    public ReturnT<String> registry(RegistryParam registryParam);

    /**
     * registry remove
     *
     * @param registryParam
     *
     * @return
     */
    public ReturnT<String> registryRemove(RegistryParam registryParam);


    // ---------------------- biz (custome) ----------------------
    // group、job ... manage
    public ReturnT<String> addJobs(List<JobParam> jobParams);

    public ReturnT<String> getGroupIdByAppName(String appName);

    public ReturnT<String> batchUpdate(List<JobParam> jobParams);

    public ReturnT<String> batchUpdateNextTimeByTaskId(List<JobParam> jobParams);

    public ReturnT<String> batchRemove(Long batchId);

    public ReturnT<String> batchStop(Long batchId);

    public ReturnT<List<JobParam>> getJobsByBatchId(Long jobBatchId);

    public ReturnT<List<JobParam>> getJobsByTaskId(String taskId);

    public ReturnT<String> removeByBatchIdAndTaskId(JobParam jobParam);
}
