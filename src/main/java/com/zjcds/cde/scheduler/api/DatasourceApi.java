package com.zjcds.cde.scheduler.api;

import com.zjcds.cde.scheduler.annotation.FeignApi;
import com.zjcds.cde.scheduler.base.ResponseResult;
import com.zjcds.cde.scheduler.domain.dto.exchange.MetaDatasourceForm;
import feign.Headers;
import feign.Param;
import feign.RequestLine;


/**
 * @author huangyj on 2019-12-11
 */
@FeignApi(serviceUrl = "http://192.168.0.69:9007")
public interface DatasourceApi {

    /**
     * 数据源信息
     * @return
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestLine("GET /cdm/meta_datasource/detail/{dsId}/{version}")
    public ResponseResult<MetaDatasourceForm.Detail> datasourceDetail(@Param("dsId") String dsId, @Param("version") String version);



}
