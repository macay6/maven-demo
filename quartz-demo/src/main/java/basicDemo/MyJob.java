package basicDemo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @ClassName: MyJobDetail
 * @Description:
 * @Author: Macay
 * @Date: 2023/5/1 2:23 下午
 */
public class MyJob implements Job {

    public MyJob() {
        System.out.println("构建Job");
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("hello quartz  " + new Date() + " job对象：" + this.toString());
    }
}
