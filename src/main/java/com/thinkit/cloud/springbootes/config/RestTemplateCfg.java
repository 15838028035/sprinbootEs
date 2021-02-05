package com.thinkit.cloud.springbootes.config;


import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class RestTemplateCfg {
	@Bean
	public RestTemplate get(){
		 RestTemplate restTemplate = new RestTemplate(); 
		 ObjectMapper newObjectMapper = new ObjectMapper();
		 newObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
		 MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter=new MappingJackson2HttpMessageConverter();
		 restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		 restTemplate.getMessageConverters().add(mappingJacksonHttpMessageConverter);
		 restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		 return restTemplate;
	}
	
	@Bean
	@ConditionalOnMissingBean({RestOperations.class, RestTemplate.class})
	public RestOperations restOperations() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setReadTimeout(5000);
		requestFactory.setConnectTimeout(5000);

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		// 使用 utf-8 编码集的 conver 替换默认的 conver（默认的 string conver 的编码集为 "ISO-8859-1"）
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
		while (iterator.hasNext()) {
			HttpMessageConverter<?> converter = iterator.next();
			if (converter instanceof StringHttpMessageConverter) {
				iterator.remove();
			}
		}
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

		return restTemplate;
	}

	
	
}
