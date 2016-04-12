package com.demo.spring.Controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MasterSpringMvcServletContext extends WebMvcConfigurerAdapter {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		CacheControl cacheControl = CacheControl.noCache().mustRevalidate();
		registry.addResourceHandler("index.html").addResourceLocations("classpath:/static/index.html")
				.setCacheControl(cacheControl);
	}
}
