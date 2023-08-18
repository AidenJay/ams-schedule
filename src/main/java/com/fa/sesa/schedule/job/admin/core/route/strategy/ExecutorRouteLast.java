package com.fa.sesa.schedule.job.admin.core.route.strategy;

import com.fa.sesa.schedule.job.admin.core.route.ExecutorRouter;
import com.fa.sesa.schedule.job.core.biz.model.ReturnT;
import com.fa.sesa.schedule.job.core.biz.model.TriggerParam;

import java.util.List;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteLast extends ExecutorRouter {

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        return new ReturnT<String>(addressList.get(addressList.size() - 1));
    }

}
