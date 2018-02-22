package com.front.ReactiveGateway.utils;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.front.ReactiveGateway.model.JdbcMetric;
import com.front.ReactiveGateway.model.JvmMetric;
import com.front.ReactiveGateway.model.ThPMetric;

public class UtilsFunctions
{
  public JvmMetric parseJvmBloc(Element e1, JvmMetric jvm)
  {
    for (Element e2 : e1.select("BoundedRangeStatistic")) {
      if (e2.attr("name").toString().equals("HeapSize")) {
        jvm.setHeapSize(Double.parseDouble(e2.attr("value")));
      }
    }
    for (Element e2 : e1.select("CountStatistic"))
    {
      if (e2.attr("name").toString().equals("UsedMemory")) {
        jvm.setUsedMemory(Double.parseDouble(e2.attr("count")));
      }
      if (e2.attr("name").toString().equals("ProcessCpuUsage")) {
        jvm.setCpuUsage(Double.parseDouble(e2.attr("count")));
      }
    }
    return jvm;
  }
  
  public List<ThPMetric> parseThreadPoolBloc(Element e1, List<ThPMetric> thp)
  {
    ThPMetric thp1 = (ThPMetric)thp.get(0);
    for (Element e2 : e1.select("BoundedRangeStatistic"))
    {
      if (e2.attr("name").toString().equals("ActiveCount")) {
        thp1.setActiveCount(Integer.parseInt(e2.attr("value")));
      }
      if (e2.attr("name").toString().equals("PoolSize")) {
        thp1.setPoolSize(Integer.parseInt(e2.attr("value")));
      }
    }
    thp.set(0, thp1);
    return thp;
  }
  
  public List<JdbcMetric> parseJDBCBloc(Element e, List<JdbcMetric> jdbcl)
  {
    JdbcMetric jdbc = (JdbcMetric)jdbcl.get(0);
    for (Element e2 : e.select("RangeStatistic"))
    {
      if (e2.attr("name").toString().equals("WaitingThreadCount")) {
        jdbc.setWaitingThreadCount(Integer.parseInt(e2.attr("value")));
      }
      if (e2.attr("name").toString().equals("WaitTime")) {
        jdbc.setWaitTime(Integer.parseInt(e2.attr("value")));
      }
    }
    for (Element e3 : e.select("BoundedRangeStatistic")) {
      if (e3.attr("name").toString().equals("PoolSize")) {
        jdbc.setPoolSize(Integer.parseInt(e3.attr("value")));
      }
    }
    jdbcl.set(0, jdbc);
    return jdbcl;
  }
  
  public List<JvmMetric> parsePerfServletObject(Document docObj, String parent)
  {
	  List<JvmMetric> server_list = new ArrayList<JvmMetric>();
	  Elements rowsServer = docObj.select("Server");
	  
	//  for (Element el1 : docObj.select("Server")) { 
      for (int i = 0; i < rowsServer.size(); i++) {
    	  Element el1 = rowsServer.get(i);

	        JvmMetric jvm1 = new JvmMetric();
	        jvm1.setParent(parent);
	        jvm1.setName(el1.attr("name"));
	        
	        //for (Element e1 : el1.select("Stat"))    {
	        Elements rowsStat1 = el1.select("Stat");
	        
	        for (int y = 0; y < rowsStat1.size(); y++) {
	      	
	          Element e1 = rowsStat1.get(y);
	          
	          if (e1.attr("name").toString().equals("JVM Runtime")) {
	            jvm1 = this.parseJvmBloc(e1, jvm1);
	          }
	          
	          ThPMetric tp1;
	          
	          if (e1.attr("name").toString().equals("Thread Pools")) {
	            for (Element e3 : e1.select("Stat"))
	            {
	              List<ThPMetric> tp = new ArrayList<ThPMetric>();
	              tp1 = new ThPMetric();
	              tp1.setPingId(jvm1.getPingId());
	              
	              tp1.setName(e3.attr("name"));
	              tp.add(tp1);
	              tp = this.parseThreadPoolBloc(e3, tp);
	              
	              jvm1.setThPool(tp);
	            }
	          }
	          if (e1.attr("name").toString().equals("Transaction Manager")) {
	            for (Element e2 : e1.select("CountStatistic"))
	            {
	              if (e2.attr("name").toString().equals("ActiveCount")) {
	                jvm1.setActiveTran(Integer.parseInt(e2.attr("count")));
	              }
	              if (e2.attr("name").toString().equals("RolledbackCount")) {
	                jvm1.setRolledbackTran(Integer.parseInt(e2.attr("count")));
	              }
	            }
	          }
	          if (e1.attr("name").toString().equals("JDBC Connection Pools")) {
	           
	        	//for (Element e4 : e1.select("Stat")) {
	        	  Elements rowsStat2 = e1.select("Stat");
	  	        
	  	          for (int z = 0; z < rowsStat2.size(); z++) {
	  	      	
	  	          Element e4 = rowsStat2.get(z);
	  	          
	              if ((!e4.attr("name").toString().equals("Fournisseur JDBC pour Microsoft SQL Server")) && (!e4.attr("name").toString().equals("Fournisseur JDBC pour Microsoft SQL Server (XA)"))) {
	            	    for (Element e5 : e4.select("Stat"))
		  	            {
	            	    	  JdbcMetric myJdbc = new JdbcMetric();
		       	              myJdbc.setPingId(jvm1.getPingId());
		       	              List<JdbcMetric> ljdbc = new ArrayList<JdbcMetric>();
		       	              myJdbc.setName(e5.attr("name"));
		       	              
		       	              ljdbc.add(myJdbc);
		       	              ljdbc = this.parseJDBCBloc(e5, ljdbc);
		       	              
		       	              jvm1.setJdbc(ljdbc);
		  	            }
	              }
	             
	            }
	          }
	          if (e1.attr("name").toString().equals("Servlet Session Manager")) {
	            for (Element e7 : e1.select("RangeStatistic"))
	            {
	              if (e7.attr("name").toString().equals("ActiveCount")) {
	                jvm1.setActiveSession(Integer.parseInt(e7.attr("value")));
	              }
	              if (e7.attr("name").toString().equals("LiveCount")) {
	                jvm1.setLiveSession(Integer.parseInt(e7.attr("value")));
	              }
	            }
	          }
	        }
	        server_list.add(jvm1);
	  }
	  return server_list;	  
  }
  
  
}

