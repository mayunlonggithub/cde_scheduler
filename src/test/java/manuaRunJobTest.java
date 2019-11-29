import com.zjcds.cde.scheduler.BootStrapApplication;
import com.zjcds.cde.scheduler.dao.jpa.RepositoryDao;
import com.zjcds.cde.scheduler.domain.entity.Repository;
import com.zjcds.cde.scheduler.utils.Constant;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.ProgressNullMonitorListener;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BootStrapApplication.class})
public class manuaRunJobTest {


//    @Resource
//    private JobQuartz jobQuartz;
    @Autowired
    private RepositoryDao repositoryDao;

    private org.pentaho.di.job.Job job;

//    @Test
//    public void manuaRunJobTest() throws KettleException {
//        KettleEnvironment.init();
//        Repository repository = repositoryDao.findByRepositoryId(1);
//        String jobPath ="/a";
////        String jobName = "数据服务连接测试";
//        String jobName = "啊";
//        String logLevel = "5";
//        manualRunRepositoryJob(repository,jobName,jobPath);
//    }

//    public void manualRunRepositoryJob(Repository repository,String jobName,String jobPath) throws KettleException {
//
//        DatabaseMeta databaseMeta = new DatabaseMeta(null, repository.getRepositoryType(), repository.getDatabaseAccess(),
//                repository.getDatabaseHost(), repository.getDatabaseName(), repository.getDatabasePort(), repository.getDatabaseUsername(), repository.getDatabasePassword());
//        databaseMeta.addExtraOption(databaseMeta.getPluginId(), "characterEncoding", "UTF-8");
//        databaseMeta.addExtraOption(databaseMeta.getPluginId(), "useUnicode", "true");
//        //资源库元对象
//        KettleDatabaseRepositoryMeta repositoryInfo = new KettleDatabaseRepositoryMeta();
//        repositoryInfo.setConnection(databaseMeta);
//        //资源库
//        KettleDatabaseRepository kettleDatabaseRepository = new KettleDatabaseRepository();
//        kettleDatabaseRepository.init(repositoryInfo);
//        kettleDatabaseRepository.connect("admin", "admin");
//        //判断是否连接成功
//        if (kettleDatabaseRepository.isConnected()) {
//            System.out.println( "connected" );
//        }else{
//            System.out.println("error");
//        }
////        RepositoryDirectoryInterface jobDirectory = repository1.loadRepositoryDirectoryTree().findDirectory("");
////        runRepositoryJob(repository, "/a", jobName);
////        runRepositoryJob(repository, "/ORACLE管控平台/ORACLE_数据服务连接测试/", jobName);
//        RepositoryDirectoryInterface directory = kettleDatabaseRepository.loadRepositoryDirectoryTree()
//                .findDirectory(jobPath);
//        JobMeta jobMeta = kettleDatabaseRepository.loadJob(jobName,directory,new ProgressNullMonitorListener(),null);
//        jobMeta.setParameterValue("name","3");
//        job = new Job(kettleDatabaseRepository, jobMeta);
//        job.setDaemon(true);
//        job.setLogLevel(LogLevel.DEBUG);
//        if (StringUtils.isNotEmpty("5")) {
//            job.setLogLevel(Constant.logger("5"));
//        }
//        try {
//            job.run();
//            job.waitUntilFinished();
//        } catch (Exception e) {
//
//        } finally {
//
//        }
//    }

//    public void runRepositoryJob(Object KRepositoryObject,
//                                 String jobPath, String jobName) throws KettleException {
//        Repository kRepository = (Repository) KRepositoryObject;
//
////        kRepository.setDatabaseName(kRepository.getDatabaseName()+"?useUnicode=true&characterEncoding=utf8");
//
//        Integer repositoryId = kRepository.getRepositoryId();
//        KettleDatabaseRepository kettleDatabaseRepository = null;
//        if (RepositoryUtil.KettleDatabaseRepositoryCatch.containsKey(repositoryId)) {
//            kettleDatabaseRepository = RepositoryUtil.KettleDatabaseRepositoryCatch.get(repositoryId);
//        } else {
//            kettleDatabaseRepository = RepositoryUtil.connectionRepository(kRepository);
//        }
//        if (null != kettleDatabaseRepository) {
//            RepositoryDirectoryInterface directory = kettleDatabaseRepository.loadRepositoryDirectoryTree()
//                    .findDirectory(jobPath);
//            JobMeta jobMeta = kettleDatabaseRepository.loadJob(jobName, directory, new ProgressNullMonitorListener(),
//                    null);
//            jobMeta.setParameterValue("name","2");
//            job = new Job(kettleDatabaseRepository, jobMeta);
//            job.setDaemon(true);
//            job.setLogLevel(LogLevel.DEBUG);
//            if (StringUtils.isNotEmpty("5")) {
//                job.setLogLevel(Constant.logger("5"));
//            }
//            try {
//                job.run();
//                job.waitUntilFinished();
//            } catch (Exception e) {
//
//            } finally {
//
//            }
//        }
//    }
}
