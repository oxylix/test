package com.front.ReactiveGateway.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ThPMetric
{
  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private int poolSize;
  private double activeCount;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="PING_ID")
  private Date pingId;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="jvmMetricId")
  @JsonBackReference
  private JvmMetric jvmMetricId;
  
  public JvmMetric getJvmMetricId()
  {
    return this.jvmMetricId;
  }
  
  public void setJvmMetricId(JvmMetric jvmMetricId)
  {
    this.jvmMetricId = jvmMetricId;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public double getPoolSize()
  {
    return this.poolSize;
  }
  
  public double getActiveCount()
  {
    return this.activeCount;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public void setPoolSize(int p)
  {
    this.poolSize = p;
  }
  
  public void setActiveCount(int ac)
  {
    this.activeCount = ac;
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
  
  public void setActiveCount(double activeCount)
  {
    this.activeCount = activeCount;
  }
}
