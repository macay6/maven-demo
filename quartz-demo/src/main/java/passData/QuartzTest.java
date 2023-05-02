package passData;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @ClassName: QuartzTest
 * @Description:
 * @Author: Macay
 * @Date: 2023/5/1 2:26 下午
 */
public class QuartzTest {
    public static void main(String[] args) throws SchedulerException {
        //创建 Scheduler 实例
        Scheduler scheduler= StdSchedulerFactory.getDefaultScheduler();
        //创建Job
        JobDetail jobDetail= JobBuilder.newJob(MyJob3.class)
                .withIdentity("job1","group1")
                .usingJobData("message","macay设置JobDetail 消息")
                .usingJobData("count",207)
                .build();
        //获取相应的信息
        //创建 Trigger
        Trigger trigger= TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1")
                .startNow()
                .usingJobData("message","macay设置Trigger 消息")
                .usingJobData("code",200)
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(10))
                .build();

        //关联一下
        scheduler.scheduleJob(jobDetail,trigger);


        //启动
        scheduler.start();
    }
}
