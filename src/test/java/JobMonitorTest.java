import com.zjcds.cde.scheduler.BootStrapApplication;
import com.zjcds.cde.scheduler.domain.dto.JobMonitorForm;
import com.zjcds.cde.scheduler.service.JobRecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BootStrapApplication.class})
public class JobMonitorTest {

    @Autowired
    private JobRecordService jobRecordService;

    @Test
    public void statis(){
        List<JobMonitorForm.JobMonitorStatis> jobMonitorStatis =  jobRecordService.getListToday(1);
        System.out.println(jobMonitorStatis);
    }
}
