package com.zjcds.cde.scheduler.service.exchange.Impl;

import com.zjcds.cde.scheduler.api.DatasourceApi;
import com.zjcds.cde.scheduler.dao.jpa.TransDao;
import com.zjcds.cde.scheduler.domain.dto.exchange.MetaDatasourceForm;
import com.zjcds.cde.scheduler.domain.dto.exchange.TableInputForm;
import com.zjcds.cde.scheduler.domain.dto.exchange.TableOutputForm;
import com.zjcds.cde.scheduler.domain.dto.exchange.TransTabInTabOutForm;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import com.zjcds.cde.scheduler.domain.entity.Repository;
import com.zjcds.cde.scheduler.domain.entity.Trans;
import com.zjcds.cde.scheduler.service.InitializeService;
import com.zjcds.cde.scheduler.service.QuartzService;
import com.zjcds.cde.scheduler.service.RepositoryService;
import com.zjcds.cde.scheduler.service.exchange.TransTabInTabOutService;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import static com.zjcds.cde.scheduler.object.db.Database.databaseMeta;
import static com.zjcds.cde.scheduler.object.db.Database.saveTrans;
import static com.zjcds.cde.scheduler.object.trans.common.NewTrans.createTrans;
import static com.zjcds.cde.scheduler.object.trans.common.TransHop.createHop;
import static com.zjcds.cde.scheduler.object.trans.input.TableInput.createTableInput;
import static com.zjcds.cde.scheduler.object.trans.output.TableOutput.createTableOutput;


@Service
public class TransTabInTabOutServiceImpl implements TransTabInTabOutService {

    @Resource
    private DatasourceApi datasourceApi;
    @Resource
    private InitializeService initialize;
    @Autowired
    private QuartzService quartzService;
    @Autowired
    private TransDao transDao;
    @Autowired
    private RepositoryService repositoryService;


    /**
     *
     * @throws Exception
     */
    @Override
    public void addTrans(TransTabInTabOutForm.TransTabInTabOut transTabInTabOut) throws Exception {
        //初始化
        KettleEnvironment.init();
        TransMeta transMeta = generateTrans(transTabInTabOut.getTransName(),transTabInTabOut.getTblIn(),transTabInTabOut.getTblOut());
        //连接到资源库
        Repository repository = repositoryService.getRepository(3);
        Assert.notNull(repository,"请现在平台添加资源库信息！");
        KettleDatabaseRepository kettleDatabaseRepository = initialize.init(repository);
        //保存
        saveTrans(kettleDatabaseRepository, transMeta,transTabInTabOut.getDirName());
        //保存策略
        Quartz quartz = quartzService.addQuartz(transTabInTabOut.getAddQuartz(),3);
        //保存转换信息
        Trans tran = new Trans();
        tran.setTransName(transTabInTabOut.getTransName());
        tran.setTransType(1);
        tran.setTransPath(transTabInTabOut.getDirName());
        tran.setTransRepositoryId(repository.getRepositoryId());
        tran.setTransLogLevel("basic");
        tran.setDelFlag(1);
        tran.setCreateUser(3);
        tran.setModifyUser(3);
        tran.setTransQuartz(quartz.getQuartzId());
        transDao.save(tran);
    }

    /**
     * 定义一个转换，但是还没有保存到资源库
     *
     * @return
     * @throws KettleException
     */
    public TransMeta generateTrans(String transName, TableInputForm.TableInputParam tblIn, TableOutputForm.TableOutputParam tblOut) throws KettleException {
        TransMeta transMeta = createTrans(transName);
        // 创建步骤1并添加到转换中
        Assert.notNull(tblIn,"表输入参数不能为空");
        MetaDatasourceForm.Detail detailIn = datasourceApi.datasourceDetail(tblIn.getDsId(),tblIn.getVersion()).getData();
        DatabaseMeta databaseMetaIn = databaseMeta(detailIn);
        transMeta.addDatabase(databaseMetaIn);
        StepMeta stepIn = createTableInput(databaseMetaIn,tblIn.getSql(),tblIn.getStepName());
        transMeta.addStep(stepIn);
        // 创建步骤2并添加到转换中
        Assert.notNull(tblOut,"表输出参数不能为空");
        MetaDatasourceForm.Detail detailOut = datasourceApi.datasourceDetail(tblOut.getDsId(),tblOut.getVersion()).getData();
        DatabaseMeta databaseMetaOut = databaseMeta(detailOut);
        transMeta.addDatabase(databaseMetaOut);
        StepMeta stepOut = createTableOutput(databaseMetaOut,tblOut.getTableName(),tblOut.getTableColumn(),tblOut.getStreamColumn(),tblOut.getStepName());
        transMeta.addStep(stepOut);
        // 创建hop连接并添加hop
        TransHopMeta TransHopMeta = createHop(stepIn, stepOut);
        transMeta.addTransHop(TransHopMeta);
        return transMeta;
    }
}
