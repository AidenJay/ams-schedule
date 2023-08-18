package com.fa.sesa.schedule.job.core.handler;

import com.fa.sesa.schedule.job.core.executor.impl.XxlJobSpringExecutor;
import com.fa.sesa.schedule.job.core.util.GsonTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;

import javax.annotation.Resource;
import java.util.*;

/**
 * 封装AMS特殊方法，简化用户使用
 *
 * @author songpy
 * @version 6.0.0
 * @date 2021/10/27
 **/
public abstract class AmsCustomJob extends IJobHandler {
    protected static Logger logger = LoggerFactory.getLogger(AmsCustomJob.class);

    @Resource
    AmsTaskService amsTaskService;

    protected class MsgObject {

        private String              appId;
        private String              type    = "EMAIL";
        private String              content;
        private String              title;
        private Map<String, Object> message = new HashMap<>();

        public MsgObject(String appId, String title, String content) {
            this.appId = appId;
            this.content = content;
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Map<String, Object> getMessage() {
            return message;
        }

        public void setMessage(Map<String, Object> message) {
            this.message = message;
        }
    }

    protected String buildMsg(Map<String, Object> data, MsgObject msgObject) {
        Map<String, Object> msgMap = msgObject.getMessage();
        msgMap.put("title", msgObject.title);
        msgMap.put("content", msgObject.content);
        msgMap.putAll(data);
        msgObject.setMessage(msgMap);
        return GsonTool.toJson(msgObject);
    }

    protected void pushDatas(List<Map<String, Object>> datas, MsgObject msgObject) throws Exception {
        if (Objects.isNull(amsTaskService)) {
            amsTaskService = XxlJobSpringExecutor.getApplicationContext().getBean(AmsTaskService.class);
        }

        for (Map<String, Object> data : datas) {
            String msg = buildMsg(data, msgObject);
            amsTaskService.execute(msg);
        }
    }


    protected void executeSql(String sql, String appId, String mailTitle, String mailContent, @Nullable Object... args)
        throws Exception {

        List<Map<String, Object>> datas = new ArrayList<>();
        String jdbcTemplateName = "jdbcTemplate";
        if (appId.equalsIgnoreCase("MES")) {
            jdbcTemplateName = "mesJdbcTemplate";
        } else if (appId.equalsIgnoreCase("QMS")) {
            jdbcTemplateName = "qmsJdbcTemplate";
        } else if (appId.equalsIgnoreCase("AMS")) {
            jdbcTemplateName = "amsJdbcTemplate";
        }

        JdbcTemplate jdbcTemplate = (JdbcTemplate) XxlJobSpringExecutor.getApplicationContext().getBean(
            jdbcTemplateName);
        datas = jdbcTemplate.queryForList(sql, args);
        if (datas.isEmpty()) {
            return;
        }

        pushDatas(datas, new MsgObject(appId, mailTitle, mailContent));
    }
}
