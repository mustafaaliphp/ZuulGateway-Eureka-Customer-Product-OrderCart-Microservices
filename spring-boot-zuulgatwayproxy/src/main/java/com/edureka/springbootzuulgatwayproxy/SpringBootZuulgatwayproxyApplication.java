package com.edureka.springbootzuulgatwayproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.edureka.springbootzuulgatwayproxy.filters.ErrorFilter;
import com.edureka.springbootzuulgatwayproxy.filters.PostFilter;
import com.edureka.springbootzuulgatwayproxy.filters.PreFilter;
import com.edureka.springbootzuulgatwayproxy.filters.RouteFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class SpringBootZuulgatwayproxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootZuulgatwayproxyApplication.class, args);
	}

	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}
	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}
	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}
	@Bean
	public RouteFilter routeFilter() {
		return new RouteFilter();
	}
}
