package startAndEndDate;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: MyJobDetail
 * @Description:
 * @Author: Macay
 * @Date: 2023/5/1 2:23 下午
 */
// 获取开始时间和结束时间
public class MyJob6 implements Job {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //格式化时间
        String dateString=sdf.format(new Date());
        //是设置的开始时间和结束时间，不会改变
        System.out.println("开始时间:"+sdf.format(jobExecutionContext.getTrigger().getStartTime()));
        System.out.println("结束时间:"+sdf.format(jobExecutionContext.getTrigger().getEndTime()));
        System.out.println("当前时间是:"+dateString);
        System.out.println("  ");
    }
}
