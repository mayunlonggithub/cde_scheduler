package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.domain.dto.CdmJobForm;
import com.zjcds.cde.scheduler.domain.entity.CdmJob;
import org.pentaho.di.core.exception.KettleException;

import java.text.ParseException;
import java.util.List;

/**
 * @author J on 20191118
 */
public interface CdmJobService {

    public void cdmJobExecute(CdmJobForm.CdmJobParam cdmJobParam,Integer uId) throws KettleException, ParseException;

    public List<CdmJob> cdmJobName();

//    public void cdmJobStop(CdmJobForm.CdmJobParam cdmJobParam,Integer uId) throws KettleException;
}
