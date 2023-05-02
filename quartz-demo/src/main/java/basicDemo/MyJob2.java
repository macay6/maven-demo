package basicDemo;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: MyJobDetail
 * @Description:
 * @Author: Macay
 * @Date: 2023/5/1 2:23 下午
 */
public class MyJob2 implements Job {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString=sdf.format(new Date());

        //通过 jobExecutionContext 来获取对象
        JobKey jobKey=jobExecutionContext.getJobDetail().getKey();

        System.out.println("名称:"+jobKey.getName());
        System.out.println("组名:"+jobKey.getGroup());
        System.out.println("类名:"+jobExecutionContext.getJobDetail().getJobClass().getName());
        System.out.println("简单类名:"+jobExecutionContext.getJobDetail().getJobClass().getSimpleName());

        //获取 Trigger 的信息
        Trigger trigger=jobExecutionContext.getTrigger();
        System.out.println("Trigger名称:"+trigger.getKey().getName());
        System.out.println("Trigger组名:"+trigger.getKey().getGroup());

        //获取运行时信息
        System.out.println("当前执行时间:"+sdf.format(jobExecutionContext.getFireTime()));
        System.out.println("下一次执行时间:"+sdf.format(jobExecutionContext.getNextFireTime()));
        System.out.println("正在备份数据库:"+dateString);
    }
}
