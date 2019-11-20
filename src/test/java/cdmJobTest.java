import com.zjcds.cde.scheduler.BootStrapApplication;
import com.zjcds.cde.scheduler.dao.jpa.RepositoryDao;
import com.zjcds.cde.scheduler.domain.dto.RepositoryTreeForm;
import com.zjcds.cde.scheduler.domain.entity.Repository;
import com.zjcds.cde.scheduler.service.CdmJobService;
import com.zjcds.cde.scheduler.utils.RepositoryUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositoryElementMetaInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BootStrapApplication.class})
public class cdmJobTest {

    @Autowired
    private CdmJobService cdmJobService;
    @Autowired
    private RepositoryDao repositoryDao;


//    @Test
//    public void cdmJobTest() throws KettleException {
//        Map<String,String> param = new HashMap<>();
//        param.put("name","5");
//        param.put("name1","a");
//        cdmJobService.cdmJobExecute(1,"调用测试",param);
//    }


    @Test
    public void cdmJobInfo() throws KettleException{
        KettleEnvironment.init();
        Repository repository = repositoryDao.findOne(1);
        KettleDatabaseRepository kettleDatabaseRepository = RepositoryUtil.connectionRepository(repository);
        //获取当前的路径信息
        RepositoryDirectoryInterface rDirectory = kettleDatabaseRepository.loadRepositoryDirectoryTree().findDirectory("/");
        //获取Directory信息
        List<RepositoryTreeForm> repositoryTreeList = RepositoryUtil.getDirectory(kettleDatabaseRepository, rDirectory);
        //获取Job和Transformation的信息
        List<RepositoryElementMetaInterface> li = kettleDatabaseRepository.getJobAndTransformationObjects(rDirectory.getObjectId(), false);

        System.out.println();
    }

}
