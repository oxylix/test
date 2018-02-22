package com.front.ReactiveGateway.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class JvmMetric {
	  @Id
	  @GeneratedValue
	  private Long id;
	  @Temporal(TemporalType.TIMESTAMP)
	  @Column(name="PING_ID")
	  private Date pingId;
	  private String name;
	  private int heapSize;
	  private int usedMemory;
	  private double cpuUsage;
	  private int activeTran;
	  private int rolledbackTran;
	  private int liveSession = 0;
	  private int activeSession = 0;
	  private String parent;
	  
	  @OneToMany(cascade={javax.persistence.CascadeType.PERSIST}, orphanRemoval=true)
	  @JsonManagedReference
	  private List<ThPMetric> thPool = new ArrayList<ThPMetric>();
	  @OneToMany(cascade={javax.persistence.CascadeType.PERSIST}, orphanRemoval=true)
	  @JsonManagedReference
	  private List<JdbcMetric> jdbc = new ArrayList<JdbcMetric>();
	  
	  public JvmMetric()
	  {
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    this.pingId = timestamp;
	  }
	  
	  public String getName()
	  {
	    return this.name;
	  }
	  
	  public int getHeapSize()
	  {
	    return this.heapSize;
	  }
	  
	  public int getUsedMemory()
	  {
	    return this.usedMemory;
	  }
	  
	  public int getActiveTran()
	  {
	    return this.activeTran;
	  }
	  
	  public int getRolledbackTran()
	  {
	    return this.rolledbackTran;
	  }
	  
	  public double getCpuUsage()
	  {
	    return this.cpuUsage;
	  }
	  
	  public int getActiveSession()
	  {
	    return this.activeSession;
	  }
	  
	  public int getLiveSession()
	  {
	    return this.liveSession;
	  }
	  
	  @OneToMany(fetch=FetchType.LAZY, mappedBy="jvm")
	  public List<ThPMetric> getThPool()
	  {
	    return this.thPool;
	  }
	  
	  @OneToMany(fetch=FetchType.EAGER, mappedBy="jvm")
	  public List<JdbcMetric> getJdbc()
	  {
	    return this.jdbc;
	  }
	  
	  public void setName(String name)
	  {
	    this.name = name;
	  }
	  
	  public void setHeapSize(double heap)
	  {
	    this.heapSize = ((int)Math.round(heap * 0.001D));
	  }
	  
	  public void setUsedMemory(double usedheap)
	  {
	    this.usedMemory = ((int)Math.round(usedheap * 0.001D));
	  }
	  
	  public void setCpuUsage(double cpu)
	  {
	    this.cpuUsage = cpu;
	  }
	  
	  public void setActiveTran(int nb)
	  {
	    this.activeTran = nb;
	  }
	  
	  public void setActiveSession(int asession)
	  {
	    this.activeSession = asession;
	  }
	  
	  public void setLiveSession(int lsession)
	  {
	    this.liveSession = lsession;
	  }
	  
	  public void setRolledbackTran(int nb)
	  {
	    this.rolledbackTran = nb;
	  }
	  
	  public void setThPool(List<ThPMetric> tps)
	  {
	    for (ThPMetric th : tps)
	    {
	      th.setJvmMetricId(this);
	      th.setPingId(this.pingId);
	      this.thPool.add(th);
	    }
	  }
	  
	  public void setJdbc(List<JdbcMetric> jdbcs)
	  {
	    for (JdbcMetric jdbc : jdbcs)
	    {
	      jdbc.setJvmMetricId(this);
	      jdbc.setPingId(this.pingId);
	      this.jdbc.add(jdbc);
	    }
	  }
	  
	  public Date getPingId()
	  {
	    return this.pingId;
	  }
	  
	  public void setPingId(Date pingId)
	  {
	    this.pingId = pingId;
	  }
	  
	  public Long getId()
	  {
	    return this.id;
	  }
	  
	  public void setId(Long id)
	  {
	    this.id = id;
	  }
	  
	  public void setHeapSize(int heapSize)
	  {
	    this.heapSize = heapSize;
	  }
	  
	  public void setUsedMemory(int usedMemory)
	  {
	    this.usedMemory = usedMemory;
	  }

	public String getParent() {
		return parent;
	}
	
	public void setParent(String parent) {
		this.parent = parent;
	}
  
}
