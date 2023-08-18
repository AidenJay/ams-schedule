package com.fa.sesa.schedule.job.admin;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@SpringBootApplication
@EnableEncryptableProperties
public class ScheduleJobAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleJobAdminApplication.class, args);
    }

}