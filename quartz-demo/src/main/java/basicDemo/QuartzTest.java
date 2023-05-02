package basicDemo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @ClassName: QuartzTest
 * @Description:
 * @Author: Macay
 * @Date: 2023/5/1 2:26 下午
 */
public class QuartzTest {
    public static void main(String[] args) {
        try {
            // 获取默认的调度器实例
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // 定义一个简单的任务
            JobDetail job = JobBuilder.newJob(MyJob.class)
                    .withIdentity("job1", "group1")
                    .build();

            //获取相应的信息

            System.out.println("jobDetail名称:"+job.getKey().getName());
            //组名如果不写的话，默认是 DEFAULT
            System.out.println("jobDetail组名:"+job.getKey().getGroup());
            //获取运行的类
            System.out.println("job类名:"+job.getJobClass().getName());


            // 定义一个简单的触发器: 每隔 3 秒执行 1 次，任务永不停止
            SimpleTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder
                            .simpleSchedule()
                            .withIntervalInSeconds(3)
                            .repeatForever()
                    ).build();

            System.out.println("trigger名称:"+trigger.getKey().getName());
            System.out.println("trigger组名:"+trigger.getKey().getGroup());

            // 关联 job和 trigger
            scheduler.scheduleJob(job, trigger);

            // 启动 scheduler
            scheduler.start();

            // 等待任务执行一些时间
            Thread.sleep(10000);

            // 关闭调度器
            scheduler.shutdown();
        } catch (Exception se) {
            se.printStackTrace();
        }
    }
}
