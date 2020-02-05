package com.workwaves.app.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 
 * @author Nicholas Marsden
 *EnableEurekaClient
 *Enable spring boot applicaion to work as an eureka client
 *
 *EnableZuulProxy
 *Enables application to work as zule application gateway
 *
 */

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class LocalServiceZuulApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalServiceZuulApiGatewayApplication.class, args);
	}

}
