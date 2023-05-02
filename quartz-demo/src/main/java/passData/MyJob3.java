package passData;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: MyJobDetail
 * @Description:
 * @Author: Macay
 * @Date: 2023/5/1 2:23 下午
 */
public class MyJob3 implements Job {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString=sdf.format(new Date());

        //通过 jobExecutionContext 来获取对象
        JobKey jobKey=jobExecutionContext.getJobDetail().getKey();
        System.out.println("名称:"+jobKey.getName());

        //获取JobDetaMap 数据对象集合
        JobDataMap dataMap= jobExecutionContext.getJobDetail().getJobDataMap();

        System.out.println("JobDetail message:"+dataMap.getString("message"));
        System.out.println("JobDetail code:"+dataMap.getInt("count"));
        System.out.println("JobDetail 无Key:"+dataMap.getString("jobDetail"));

        System.out.println(" ");
        //获取 Trigger 的集合
        Trigger trigger=jobExecutionContext.getTrigger();
        System.out.println("Trigger名称:"+trigger.getKey().getName());

        //获取Trigger 里面的Map数据
        JobDataMap triggerMap=jobExecutionContext.getTrigger().getJobDataMap();
        System.out.println("Trigger message:"+triggerMap.get("message"));
        System.out.println("Trigger code:"+triggerMap.getInt("code"));
        System.out.println("Trigger 无key:"+triggerMap.getString("trigger"));


        String message = jobExecutionContext.getMergedJobDataMap().get("message").toString();
        String count = jobExecutionContext.getMergedJobDataMap().get("count").toString();
        String code = jobExecutionContext.getMergedJobDataMap().get("code").toString();
        System.out.println(message);
        System.out.println(count);
        System.out.println(code);
    }
}
