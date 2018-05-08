package ru.gothmog.houses.config;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.gothmog.houses.service.HouseService;

import java.util.Date;

@Component
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;
    @Autowired
    private HouseService houseService;

    /**The pattern is a list of six single space-separated fields:
     * representing second, minute, hour, day, month, weekday.
     * Month and weekday names can be given as the first three letters of the English names.
     * Example patterns:

     "0 0 * * * *" = the top of every hour of every day.*
     "*\/10 * * * * *" = every ten seconds. Remove 2nd character, it is escape
     "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
     "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
     "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
     "0 0 0 25 12 ?" = every Christmas Day at midnight

     */
    @Scheduled(cron =  "*/10 * * * * *")//0 0/30 * * * TUE-SAT
    public void runJob() throws Exception {
       // jobLauncher.run(job, new JobParameters());
        Integer countFrom = houseService.countRowFromHouse();
        Integer countTo = houseService.countRowToHouse();
        int fromCount = countFrom.intValue();
        System.out.println("fromCount = " + fromCount);
        int toCount = countTo.intValue();
        System.out.println("toCount = " + toCount);
        if (toCount==0 || fromCount!= toCount){
            houseService.createTableStatus();
            System.out.println("таблица пересоздана");
            JobParametersBuilder builder = new JobParametersBuilder();
            builder.addDate("date", new Date());
            jobLauncher.run(job, builder.toJobParameters());
         //   jobProcces();
//        }else if (houseService.countRowFromHouse()!= houseService.countRowToHouse()){
//            houseService.dropTable();
//            houseService.createTable();
//            jobProcces();
        }else {
            System.out.println("таблицы идентичны");
        }
    }

    private void jobProcces() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addDate("date", new Date());
        jobLauncher.run(job, builder.toJobParameters());
//        try {
//         JobParameters jobParameters =
//                    new JobParametersBuilder()
//                            .addLong("time",System.currentTimeMillis()).toJobParameters();
//            JobExecution execution = jobLauncher.run(job, new JobParameters());
//            System.out.println("Exit Status : " + execution.getStatus());
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
