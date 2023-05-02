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
public class MyJob4 implements Job {

    private String message;

    private int count;

    private int code;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(message);
        System.out.println(count);
        System.out.println(code);
    }
}
