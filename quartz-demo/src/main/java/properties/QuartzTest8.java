package properties;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @ClassName: QuartzTest
 * @Description:
 * @Author: Macay
 * @Date: 2023/5/1 2:26 下午
 */
public class QuartzTest8 {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        //创建 Scheduler 实例
        Scheduler scheduler= StdSchedulerFactory.getDefaultScheduler();
        //创建Job
        JobDetail jobDetail= JobBuilder.newJob(MyJob8.class)
                .withIdentity("job1","group1")
                .build();
        //获取相应的信息
        //创建 Trigger
        Trigger trigger= TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2))
                .build();

        //关联一下
        scheduler.scheduleJob(jobDetail,trigger);


        //启动
        scheduler.start();

    }
}
