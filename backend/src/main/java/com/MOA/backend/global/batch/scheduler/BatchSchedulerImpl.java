package com.MOA.backend.global.batch.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
        JobRegistryBeanPostProcessor jobProcessor = new JobRegistryBeanPostProcessor();
        jobProcessor.setJobRegistry(jobRegistry);
        return jobProcessor;
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void runJob() {
        String time = LocalDateTime.now().toString();
        try {
            Job job = jobRegistry.getJob("testJob");
            JobParametersBuilder jobParam = new JobParametersBuilder().addString("time", time);
            jobLauncher.run(job, jobParam.toJobParameters());
        } catch (Exception e) {
            throw new RuntimeException("Batch 관련 문제가 발생했습니다. {}", e);
        }
    }
}
