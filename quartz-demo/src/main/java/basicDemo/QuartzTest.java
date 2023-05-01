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

            // 打开调度器
            scheduler.start();

            // 定义一个简单的任务
            JobDetail job = JobBuilder.newJob(MyJobDetail.class)
                    .withIdentity("job1", "group1")
                    .build();

            // 定义一个简单的触发器: 每隔 3 秒执行 1 次，任务永不停止
            SimpleTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder
                            .simpleSchedule()
                            .withIntervalInSeconds(3)
                            .repeatForever()
                    ).build();

            // 开始调度任务
            scheduler.scheduleJob(job, trigger);

            // 等待任务执行一些时间
            Thread.sleep(10000);

            // 关闭调度器
            scheduler.shutdown();
        } catch (Exception se) {
            se.printStackTrace();
        }
    }
}
