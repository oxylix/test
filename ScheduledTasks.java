package com.front.ReactiveGateway.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTasks
{
 // private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
  @Value("${server.port}")
  private String server_port;
  
  @Scheduled(fixedRate=300000L)
  public void pingAndSavePerfServletMetrics()
  {
    String uri = "http://localhost:" + this.server_port + "/api/ping/save";
    RestTemplate restTemplate = new RestTemplate();
    String result = (String)restTemplate.getForObject(uri, String.class, new Object[0]);
   // log.info("Ping:  {}", result);
  }
}
