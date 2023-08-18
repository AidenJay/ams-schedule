package com.fa.sesa.schedule.job.admin.controller;

import com.fa.sesa.schedule.job.admin.controller.annotation.PermissionLimit;
import com.fa.sesa.schedule.job.admin.core.conf.XxlJobAdminConfig;
import com.fa.sesa.schedule.job.core.biz.AdminBiz;
import com.fa.sesa.schedule.job.core.biz.model.HandleCallbackParam;
import com.fa.sesa.schedule.job.core.biz.model.JobParam;
import com.fa.sesa.schedule.job.core.biz.model.RegistryParam;
import com.fa.sesa.schedule.job.core.biz.model.ReturnT;
import com.fa.sesa.schedule.job.core.util.GsonTool;
import com.fa.sesa.schedule.job.core.util.XxlJobRemotingUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by xuxueli on 17/5/10.
 */
@Controller
@RequestMapping("/api")
public class JobApiController {

    @Resource
    private AdminBiz adminBiz;

    /**
     * api
     *
     * @param uri
     * @param data
     * @return
     */
    @RequestMapping("/{uri}")
    @ResponseBody
    @PermissionLimit(limit = false)
    public ReturnT<String> api(HttpServletRequest request, @PathVariable("uri") String uri,
                               @RequestBody(required = false) String data) {

        // valid
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "invalid request, HttpMethod not support.");
        }
        if (uri == null || uri.trim().length() == 0) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "invalid request, uri-mapping empty.");
        }
        if (XxlJobAdminConfig.getAdminConfig().getAccessToken() != null &&
                XxlJobAdminConfig.getAdminConfig().getAccessToken().trim().length() > 0 &&
                !XxlJobAdminConfig.getAdminConfig().getAccessToken()
                                  .equals(request.getHeader(XxlJobRemotingUtil.XXL_JOB_ACCESS_TOKEN))) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "The access token is wrong.");
        }

        // services mapping
        if ("callback".equals(uri)) {
            List<HandleCallbackParam> callbackParamList = GsonTool.fromJson(data, List.class,
                                                                            HandleCallbackParam.class);
            return adminBiz.callback(callbackParamList);
        } else if ("registry".equals(uri)) {
            RegistryParam registryParam = GsonTool.fromJson(data, RegistryParam.class);
            return adminBiz.registry(registryParam);
        } else if ("registryRemove".equals(uri)) {
            RegistryParam registryParam = GsonTool.fromJson(data, RegistryParam.class);
            return adminBiz.registryRemove(registryParam);
        } else if ("addBatchJobs".equals(uri)) {
            List<JobParam> jobParam = GsonTool.fromJson(data, List.class, JobParam.class);
            return adminBiz.addJobs(jobParam);
        } else if ("getGroupId".equals(uri)) {
            String appName = GsonTool.fromJson(data, String.class);
            return adminBiz.getGroupIdByAppName(appName);
        } else if ("batchUpdateJob".equals(uri)) {
            List<JobParam> jobParams = GsonTool.fromJson(data, List.class, JobParam.class);
            return adminBiz.batchUpdate(jobParams);
        } else if ("batchRemoveJob".equals(uri)) {
            long jobBatchId = GsonTool.fromJson(data, long.class);
            return adminBiz.batchRemove(jobBatchId);
        } else if ("batchStopJob".equals(uri)) {
            long jobBatchId = GsonTool.fromJson(data, long.class);
            return adminBiz.batchStop(jobBatchId);
        } else if ("batchUpdateNextTimeByTaskId".equals(uri)) {
            List<JobParam> jobParams = GsonTool.fromJson(data, List.class, JobParam.class);
            return adminBiz.batchUpdateNextTimeByTaskId(jobParams);
        } else if ("removeByBatchIdAndTaskId".equals(uri)) {
            JobParam jobParam = GsonTool.fromJson(data, JobParam.class);
            return adminBiz.removeByBatchIdAndTaskId(jobParam);
        } else {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "invalid request, uri-mapping(" + uri + ") not found.");
        }

    }

    @RequestMapping("/getJobsByBatchId")
    @ResponseBody
    @PermissionLimit(limit = false)
    public ReturnT<List<JobParam>> getJobsByBatchId(HttpServletRequest request,
                                                    @RequestBody(required = false) String data) {
        long jobBatchId = GsonTool.fromJson(data, long.class);
        return adminBiz.getJobsByBatchId(jobBatchId);
    }

    @RequestMapping("/getJobsByTaskId")
    @ResponseBody
    @PermissionLimit(limit = false)
    public ReturnT<List<JobParam>> getJobsByTaskId(HttpServletRequest request,
                                                   @RequestBody(required = false) String data) {
        String taskId = GsonTool.fromJson(data, String.class);
        return adminBiz.getJobsByTaskId(taskId);
    }

}
