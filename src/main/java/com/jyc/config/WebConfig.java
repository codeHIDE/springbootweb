package com.jyc.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.jyc.filter.TimeFilter;
import com.jyc.listener.ListenerTest;
import com.jyc.servlet.ServletTest;

@Configuration
public class WebConfig {

//	@Autowired
//    private TimeInterceptor timeInterceptor;
	
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        
        HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
        
        return new HttpMessageConverters(converter);

    }
    
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new ServletTest(),"/servletTest");
    }
    
    @Bean
    public FilterRegistrationBean timeFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        
        TimeFilter timeFilter = new TimeFilter();
        registrationBean.setFilter(timeFilter);
        
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        
        return registrationBean;
    }
    
    @Bean
    public ServletListenerRegistrationBean<ListenerTest> servletListenerRegistrationBean() {
        return new ServletListenerRegistrationBean<ListenerTest>(new ListenerTest());
    }
    
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(timeInterceptor);
//    }
    
}