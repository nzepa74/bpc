package com.spring.project.development.helper;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class SchedulerJob {
        @Scheduled(cron = "0 28 15 * * ?")//run every 24 hours, for production
//    @Scheduled(cron = "0 0/1 * * * ?")//run every one minute, for testing purpose only
    public void performSchedulerJob() {
        deleteJunkJasperReports();
//        sendSchedulerMail();
    }
    private void deleteJunkJasperReports() {
        String filepath = "D:\\development\\src\\main\\webapp\\reports";
        File file = new File(filepath);
        DeleteDirectory.deleteJunkJasperReports(file);
    }
}
