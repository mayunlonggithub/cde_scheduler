package com.zjcds.cde.scheduler.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * 跨域设置
 * Created date:2019-08-20
 *
 * @author shihuajie
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//        super.addResourceHandlers(registry);
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://192.168.0.191:8080","http://192.168.0.69:8889","http://47.99.134.197:8889"
                        ,"http://192.168.0.69:9108","http://192.168.0.69:8080","http://192.168.0.69:80","http://192.168.0.126:8080"
                        ,"http://192.168.0.126:8088","http://47.99.134.197:9108","http://47.99.134.197:9001","http://47.99.134.197:9011","http://47.99.134.197:9021"
                ,"http://192.168.43.228:8088","http://117.136.60.24:8088","http://192.168.0.69","http://192.168.0.69:80")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://47.99.134.197:9001","http://47.99.134.197:9011","http://47.99.134.197:9021")
//                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
//                .maxAge(3600)
//                .allowCredentials(true);
//    }
}
