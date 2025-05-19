package com.wroteit.ModerationApp;

import com.wroteit.ModerationApp.model.EntityType;
import com.wroteit.ModerationApp.model.Report;
import com.wroteit.ModerationApp.service.ModeratorService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class ModerationAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModerationAppApplication.class, args);
    }

    @Component
    public class ReportListener {

        @Autowired
        private ModeratorService moderatorService;

        @RabbitListener(queues = "moderator.report.queue")
        public void handleReport(Map<String, Object> reportData) {
            Report report = new Report(
                    Long.valueOf(reportData.get("reporterId").toString()),
                    (String) reportData.get("reportedEntityId"),
                    EntityType.valueOf((String) reportData.get("entityType")),
                    (String) reportData.get("reason"),
                    LocalDateTime.now()
            );
            report.setCommunityId((String) reportData.get("communityId"));

            moderatorService.fileReport(report);
        }
    }

}
