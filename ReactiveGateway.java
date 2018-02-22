package com.front.ReactiveGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableJpaRepositories
@EnableScheduling
public class ReactiveGateway 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(ReactiveGateway.class, args);
    }
}
