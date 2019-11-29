import com.zjcds.cde.scheduler.BootStrapApplication;
import com.zjcds.cde.scheduler.dao.jpa.JobRecordDao;
import com.zjcds.cde.scheduler.domain.entity.JobRecord;
import com.zjcds.cde.scheduler.service.JobMonitorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.sql.Date.valueOf;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BootStrapApplication.class})
public class jobAndTransLineTest {

    @Autowired
    private JobRecordDao jobRecordDao;
    @Autowired
    private JobMonitorService jobMonitorService;

    @Test
    public void jobLine() throws ParseException {
        List<JobRecord> jobRecordList = jobRecordDao.findAll();

        jobRecordList.stream().forEach(e ->e.setStartTime(valueOf(e.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())));
        Map<Date,Long> job = jobRecordList.stream().collect(Collectors.groupingBy(JobRecord :: getStartTime,Collectors.counting()));
        List<Date> dateList = new ArrayList<>();
        for (int i = -6; i <= 0; i++){
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DATE, i);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateFormat = simpleDateFormat.format(instance.getTime());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            dateList.add(format.parse(dateFormat));
        }
        Map<String, Object> transLine = new HashMap<>();
        Integer i=0;
        for(Date s : dateList){
            Integer sum ;
            if(job.get(s)==null){
                sum=0;
            }else {
                sum=job.get(s).intValue();
            }
            transLine.put(i.toString(),sum);
            i += 1;
        }
        System.out.println(job);
    }


}
