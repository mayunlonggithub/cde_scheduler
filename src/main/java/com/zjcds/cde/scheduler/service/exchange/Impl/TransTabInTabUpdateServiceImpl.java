package com.zjcds.cde.scheduler.service.exchange.Impl;

import com.zjcds.cde.scheduler.api.DatasourceApi;
import com.zjcds.cde.scheduler.domain.dto.exchange.MetaDatasourceForm;
import com.zjcds.cde.scheduler.domain.dto.exchange.TableInputForm;
import com.zjcds.cde.scheduler.domain.dto.exchange.TableUpdateForm;
import com.zjcds.cde.scheduler.domain.dto.exchange.TransTabInTabUpdateForm;
import com.zjcds.cde.scheduler.object.db.Initialize;
import com.zjcds.cde.scheduler.service.exchange.TransTabInTabUpdateService;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import static com.zjcds.cde.scheduler.object.db.Database.databaseMeta;
import static com.zjcds.cde.scheduler.object.db.Database.saveTrans;
import static com.zjcds.cde.scheduler.object.trans.common.NewTrans.createTrans;
import static com.zjcds.cde.scheduler.object.trans.common.TransHop.createHop;
import static com.zjcds.cde.scheduler.object.trans.input.TableInput.createTableInput;
import static com.zjcds.cde.scheduler.object.trans.output.TableUpdate.createTableUpdate;

/**
 * @author J on 2020/1/2
 */
@Service
public class TransTabInTabUpdateServiceImpl implements TransTabInTabUpdateService {

    @Resource
    private DatasourceApi datasourceApi;
    @Resource
    private Initialize initialize;


    /**
     *
     * @throws Exception
     */
    @Override
    public void addTrans(TransTabInTabUpdateForm.TransTabInTabUpdate transTabInTabUpdate) throws Exception {
        //初始化
        KettleEnvironment.init();
        TransMeta transMeta = generateTrans(transTabInTabUpdate.getTransName(),transTabInTabUpdate.getTblIn(),transTabInTabUpdate.getTblUpdate());
        //连接到资源库
        KettleDatabaseRepository kettleDatabaseRepository = initialize.RepositoryCon();
        //保存
        saveTrans(kettleDatabaseRepository, transMeta,transTabInTabUpdate.getDirName());
    }

    /**
     * 定义一个转换，但是还没有保存到资源库
     *
     * @return
     * @throws KettleException
     */
    public TransMeta generateTrans(String transName, TableInputForm.TableInputParam tblIn, TableUpdateForm.TableUpdateParam tblUpdate) throws KettleException {
        TransMeta transMeta = createTrans(transName);
        // 创建步骤1并添加到转换中
        Assert.notNull(tblIn,"表输入参数不能为空");
        MetaDatasourceForm.Detail detailIn = datasourceApi.datasourceDetail(tblIn.getDsId(),tblIn.getVersion()).getData();
        DatabaseMeta databaseMetaIn = databaseMeta(detailIn);
        transMeta.addDatabase(databaseMetaIn);
        StepMeta stepIn = createTableInput(databaseMetaIn,tblIn.getSql(),tblIn.getStepName());
        transMeta.addStep(stepIn);
        // 创建步骤2并添加到转换中
        Assert.notNull(tblUpdate,"表更新参数不能为空");
        MetaDatasourceForm.Detail detailOut = datasourceApi.datasourceDetail(tblUpdate.getDsId(),tblUpdate.getVersion()).getData();
        DatabaseMeta databaseMetaOut = databaseMeta(detailOut);
        transMeta.addDatabase(databaseMetaOut);
        StepMeta stepOut = createTableUpdate(databaseMetaOut,tblUpdate.getTableName(),tblUpdate.getKeyLookups(),tblUpdate.getConditions(),tblUpdate.getKeyStreams(),tblUpdate.getKeyStreams2(),tblUpdate.getTableColumn(),tblUpdate.getStreamColumn(),tblUpdate.getIsUpdate(),tblUpdate.getStepName());
        transMeta.addStep(stepOut);
        // 创建hop连接并添加hop
        TransHopMeta TransHopMeta = createHop(stepIn, stepOut);
        transMeta.addTransHop(TransHopMeta);
        return transMeta;
    }
}
