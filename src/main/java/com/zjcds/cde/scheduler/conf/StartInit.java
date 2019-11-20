package com.zjcds.cde.scheduler.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.pentaho.di.core.KettleEnvironment;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartInit implements InitializingBean {


//	@Bean("KettleEnvironmentInit")
	public void afterPropertiesSet() throws Exception {
		//初始化环境***
//		com.zhaxd.common.kettle.environment.KettleInit.init();
		KettleInit.environmentInit();
		KettleEnvironment.init();
	}

}
