package simpleScheduleBuilder;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: MyJobDetail
 * @Description:
 * @Author: Macay
 * @Date: 2023/5/1 2:23 下午
 */
public class MyJob5 implements Job {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(new Date());
        System.out.println(dateString);
    }
}
