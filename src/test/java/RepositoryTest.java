import com.zjcds.cde.scheduler.BootStrapApplication;
import com.zjcds.cde.scheduler.service.RepositoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.di.core.exception.KettleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BootStrapApplication.class})
public class RepositoryTest {

    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void saveTreeList()throws KettleException {
        repositoryService.saveTreeList(1);
    }
}
