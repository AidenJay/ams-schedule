package com.fa.sesa.schedule.job.admin.service.impl;

import com.fa.sesa.schedule.job.admin.core.model.XxlJobInfo;
import com.fa.sesa.schedule.job.admin.core.thread.JobCompleteHelper;
import com.fa.sesa.schedule.job.admin.core.thread.JobRegistryHelper;
import com.fa.sesa.schedule.job.admin.service.XxlJobService;
import com.fa.sesa.schedule.job.core.biz.AdminBiz;
import com.fa.sesa.schedule.job.core.biz.model.HandleCallbackParam;
import com.fa.sesa.schedule.job.core.biz.model.JobParam;
import com.fa.sesa.schedule.job.core.biz.model.RegistryParam;
import com.fa.sesa.schedule.job.core.biz.model.ReturnT;
import com.fa.sesa.schedule.job.core.glue.GlueTypeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:54:20
 */
@Service
public class AdminBizImpl implements AdminBiz {

    @Resource
    private XxlJobService xxlJobService;

    @Override
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        return JobCompleteHelper.getInstance().callback(callbackParamList);
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {
        return JobRegistryHelper.getInstance().registry(registryParam);
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        return JobRegistryHelper.getInstance().registryRemove(registryParam);
    }

    @Override
    public ReturnT<String> addJobs(List<JobParam> jobParams) {
        if (CollectionUtils.isEmpty(jobParams)) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "Illegal Argument.");
        }
        List<XxlJobInfo> xxlJobInfos = new ArrayList<>();
        for (JobParam jobParam : jobParams) {
            XxlJobInfo xxlJobInfo = new XxlJobInfo();
            BeanUtils.copyProperties(jobParam, xxlJobInfo);
            xxlJobInfo.setAddTime(new Date());
            setGeneralParam(xxlJobInfo);
            xxlJobInfos.add(xxlJobInfo);
        }
        return xxlJobService.addBatchJob(xxlJobInfos);
    }

    @Override
    public ReturnT<String> getGroupIdByAppName(String appName) {
        return xxlJobService.getJobGroupIdByAppName(appName);
    }

    @Override
    public ReturnT<String> batchUpdate(List<JobParam> jobParams) {
        List<XxlJobInfo> xxlJobInfos = new ArrayList<>();
        for (JobParam jobParam : jobParams) {
            XxlJobInfo xxlJobInfo = new XxlJobInfo();
            BeanUtils.copyProperties(jobParam, xxlJobInfo);
            setGeneralParam(xxlJobInfo);
            xxlJobInfos.add(xxlJobInfo);
        }
        return xxlJobService.batchUpdate(xxlJobInfos);
    }

    @Override
    public ReturnT<String> batchUpdateNextTimeByTaskId(List<JobParam> jobParams) {
        List<XxlJobInfo> xxlJobInfos = new ArrayList<>();
        for (JobParam jobParam : jobParams) {
            XxlJobInfo xxlJobInfo = new XxlJobInfo();
            BeanUtils.copyProperties(jobParam, xxlJobInfo);
            setGeneralParam(xxlJobInfo);
            xxlJobInfos.add(xxlJobInfo);
        }
        return xxlJobService.batchUpdateNextTimeByTaskId(xxlJobInfos);
    }

    @Override
    public ReturnT<String> batchRemove(Long batchId) {
        return xxlJobService.batchRemove(batchId);
    }

    @Override
    public ReturnT<String> batchStop(Long batchId) {
        return xxlJobService.batchStop(batchId);
    }

    @Override
    public ReturnT<List<JobParam>> getJobsByBatchId(Long jobBatchId) {
        List<JobParam> jobParams = new ArrayList<>();
        List<XxlJobInfo> jobInfos = xxlJobService.getJobsByBatchId(jobBatchId);
        for (XxlJobInfo jobInfo : jobInfos) {
            JobParam jobParam = new JobParam();
            BeanUtils.copyProperties(jobInfo, jobParam);
            jobParams.add(jobParam);
        }
        return new ReturnT<>(jobParams);
    }

    @Override
    public ReturnT<List<JobParam>> getJobsByTaskId(String taskId) {
        List<JobParam> jobParams = new ArrayList<>();
        List<XxlJobInfo> jobInfos = xxlJobService.getJobsByTaskId(taskId);
        for (XxlJobInfo jobInfo : jobInfos) {
            JobParam jobParam = new JobParam();
            BeanUtils.copyProperties(jobInfo, jobParam);
            jobParams.add(jobParam);
        }
        return new ReturnT<>(jobParams);
    }

    @Override
    public ReturnT<String> removeByBatchIdAndTaskId(JobParam jobParam) {
        return xxlJobService.removeByBatchIdAndTaskId(jobParam.getJobBatchId(), jobParam.getExecutorHandler());
    }

    private void setGeneralParam(XxlJobInfo xxlJobInfo) {
        //运行模式
        xxlJobInfo.setGlueType(GlueTypeEnum.BEAN.toString());
        xxlJobInfo.setUpdateTime(new Date());
        xxlJobInfo.setGlueUpdatetime(new Date());
        xxlJobInfo.setGlueRemark("GLUE代码初始化");
    }

}
