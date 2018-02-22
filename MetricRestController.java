package com.front.ReactiveGateway.controller;

import java.io.IOException;
import java.util.List;
import com.front.ReactiveGateway.model.JvmMetric;
import com.front.ReactiveGateway.repository.JvmMetricRepository;
import com.front.ReactiveGateway.utils.ScheduledTasks;
import com.front.ReactiveGateway.utils.UtilsFunctions;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricRestController
{
  private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
  @Autowired
  private JvmMetricRepository jvmRepo;
  
  private static UtilsFunctions util = new UtilsFunctions();
  
  @Value("${host.to.fetch}")
  private String host_to_fetch;
  
  @RequestMapping({"/realtime"})
  @ResponseBody
  public List<JvmMetric> dashboard(@RequestParam("hosttofetch") String hosttofetch)
  {
  //  log.info("Ping {} for real time metrics.", hosttofetch);
   
	try {  
	    String url = this.host_to_fetch;
		Document server1 = Jsoup.connect("http://" + url + "/wasPerfTool/servlet/perfservlet").maxBodySize(0).timeout(100000).get();
		
		List<JvmMetric> server_list = util.parsePerfServletObject(server1, "FILEDOC");
    
		return server_list;
		
	}catch (IOException e3) {
	    e3.printStackTrace();
	    return null;
	  }
  }
  
  	@RequestMapping("/api/realtime")
	@ResponseBody
	public List<JvmMetric> fetchRealTimeData(@RequestParam("hosttofetch") String url) {
		// log.info("Ping {} for real time metrics.", url);
		
		try {  
			Document server1 = Jsoup.connect("http://" + url + "/wasPerfTool/servlet/perfservlet").maxBodySize(0).timeout(100000).get();
			
			List<JvmMetric> server_list = util.parsePerfServletObject(server1, "FILEDOC");
	    
			return server_list;
			
		}catch (IOException e3) {
		    e3.printStackTrace();
		    return null;
		  }
	}
  
  @RequestMapping({"/api/history/{jvm_name:[a-zA-Z0-9]+}"})
  @ResponseBody
  public Page<JvmMetric> getJvmInfo(@PathVariable String jvm_name, Pageable pageable)
  {
   // log.info("Server name: {}", jvm_name);
    if (jvm_name.equalsIgnoreCase("all"))
    {
      Page<JvmMetric> jvm = this.jvmRepo.findAllByOrderByPingIdDesc(pageable);
      return jvm;
    }
    Page<JvmMetric> jvm = this.jvmRepo.findByNameOrderByPingIdDesc(jvm_name, pageable);
    return jvm;
  }
  
  @RequestMapping({"/api/ping/save"})
  @ResponseBody
  public String savePerfServletMetrics()
  {
	try {  
	    String url = this.host_to_fetch;
		Document server1 = Jsoup.connect("http://" + url + "/wasPerfTool/servlet/perfservlet").maxBodySize(0).timeout(100000).get();
		
		List<JvmMetric> server_list = util.parsePerfServletObject(server1, "FILEDOC");
		
	    if (!server_list.isEmpty()) {
	      this.jvmRepo.save(server_list);
	    }
		return "Saved OK";
		
	  }catch (IOException e3) {
	    e3.printStackTrace();
	    return "Error";
	  }
  }
  
  @RequestMapping({"/api/details/{jvmid:[0-9]+}"})
  @ResponseBody
  public JvmMetric getDetailsJvmId(@PathVariable Long jvmid)
  {
    JvmMetric jvmm = this.jvmRepo.getOneById(jvmid);
    
    return jvmm;
  }
  
  	@RequestMapping("/filedoc/api/chart") 
	@ResponseBody
	public String chartMemory() { 
		
		//RestTemplate http2Template = new RestTemplate(new OkHttp3ClientHttpRequestFactory());
	    //String http2Response = http2Template.getForObject("http://127.0.0.1:8099/api/history/CpeSRV2?page=0&size=10", String.class);
		 
	   // return http2Response;
		
		String res = "[{\"pingId\":1518427569904, \"icn1\":1228, \"icn2\":1128, \"icn3\":1528, \"icn4\":928},{\"pingId\":1518427579904, \"icn1\":800, \"icn2\":1228, \"icn3\":1328, \"icn4\":1328},{\"pingId\":1518427589904, \"icn1\":956, \"icn2\":1228, \"icn3\":1728, \"icn4\":1228},{\"pingId\":1518427599904, \"icn1\":1628, \"icn2\":1428, \"icn3\":1128, \"icn4\":1328}]";
		return res;
	} 
  
}
