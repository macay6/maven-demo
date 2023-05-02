package startAndEndDate;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @ClassName: QuartzTest
 * @Description:
 * @Author: Macay
 * @Date: 2023/5/1 2:26 下午
 */
public class QuartzTest6 {
    public static void main(String[] args) throws SchedulerException {
        //创建 Scheduler 实例
        Scheduler scheduler= StdSchedulerFactory.getDefaultScheduler();
        //创建Job
        JobDetail jobDetail= JobBuilder.newJob(MyJob6.class)
                .withIdentity("job1","group1")
                .build();
        //获取相应的信息
        //当前时间
        Date nowDate=new Date();
        //开始时间为 当前时间往后4s,即推迟4s执行
        Date startDate=new Date(nowDate.getTime()+4000);
        //结束时间，往后跑10秒
        Date endDate=new Date(startDate.getTime()+10000);

        //创建 Trigger
        Trigger trigger= TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1") //设置标识
                .startAt(startDate)
                .endAt(endDate)
                // 设置为简单触发器,2s触发一次
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(2,2))
                .build();

        //关联一下
        scheduler.scheduleJob(jobDetail,trigger);


        //启动
        scheduler.start();
    }
}
