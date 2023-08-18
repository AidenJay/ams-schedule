package com.fa.sesa.schedule.job.core.biz.client;

import com.fa.sesa.schedule.job.core.biz.AdminBiz;
import com.fa.sesa.schedule.job.core.biz.model.HandleCallbackParam;
import com.fa.sesa.schedule.job.core.biz.model.JobParam;
import com.fa.sesa.schedule.job.core.biz.model.RegistryParam;
import com.fa.sesa.schedule.job.core.biz.model.ReturnT;
import com.fa.sesa.schedule.job.core.util.XxlJobRemotingUtil;

import java.util.List;

/**
 * admin api test
 *
 * @author xuxueli 2017-07-28 22:14:52
 */
public class AdminBizClient implements AdminBiz {

    public AdminBizClient() {
    }

    public AdminBizClient(String addressUrl, String accessToken) {
        this.addressUrl = addressUrl;
        this.accessToken = accessToken;

        // valid
        if (!this.addressUrl.endsWith("/")) {
            this.addressUrl = this.addressUrl + "/";
        }
    }

    private String addressUrl;
    private String accessToken;
    private int    timeout = 3;


    @Override
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/callback", accessToken, timeout, callbackParamList,
                                           String.class);
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/registry", accessToken, timeout, registryParam,
                                           String.class);
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/registryRemove", accessToken, timeout, registryParam,
                                           String.class);
    }

    @Override
    public ReturnT<String> addJobs(List<JobParam> jobParams) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/addBatchJobs", accessToken, timeout, jobParams,
                                           String.class);
    }

    @Override
    public ReturnT<String> getGroupIdByAppName(String appName) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/getGroupId", accessToken, timeout, appName, String.class);
    }

    @Override
    public ReturnT<String> batchUpdate(List<JobParam> jobInfos) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/batchUpdateJob", accessToken, timeout, jobInfos,
                                           String.class);
    }

    @Override
    public ReturnT<String> batchRemove(Long batchId) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/batchRemoveJob", accessToken, timeout, batchId,
                                           String.class);
    }

    @Override
    public ReturnT<String> batchStop(Long batchId) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/batchStopJob", accessToken, timeout, batchId,
                                           String.class);
    }

    @Override
    public ReturnT<List<JobParam>> getJobsByBatchId(Long jobBatchId) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/getJobsByBatchId", accessToken, timeout, jobBatchId,
                                           List.class);
    }

    @Override
    public ReturnT<List<JobParam>> getJobsByTaskId(String taskId) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/getJobsByTaskId", accessToken, timeout, taskId,
                                           List.class);
    }

    @Override
    public ReturnT<String> batchUpdateNextTimeByTaskId(List<JobParam> jobParams) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/batchUpdateNextTimeByTaskId", accessToken, timeout,
                                           jobParams, String.class);
    }

    @Override
    public ReturnT<String> removeByBatchIdAndTaskId(JobParam jobParam) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/removeByBatchIdAndTaskId", accessToken, timeout, jobParam,
                                           String.class);
    }
}
