package com.jyc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.jyc.interceptor.TimeInterceptor;

@Configuration
public class InterceptorConfig  extends WebMvcConfigurationSupport{
	
	
	@Override  
    protected void addInterceptors(InterceptorRegistry registry) {  
        // TODO Auto-generated method stub  
        registry.addInterceptor(new TimeInterceptor());  
    }  
	
	
}
