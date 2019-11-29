import com.zjcds.cde.scheduler.BootStrapApplication;
import com.zjcds.cde.scheduler.service.TransService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.di.core.exception.KettleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BootStrapApplication.class})
public class manuaRunTrans {



    @Autowired
    private TransService transService;

//    @Test
//    public void runTrans() throws KettleException {
//        Map<String,String> param = new HashMap<>();
//        param.put("name","cds");
//        transService.start(204,1,param);
//    }
}
