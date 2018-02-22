package com.front.ReactiveGateway.repository;

import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.front.ReactiveGateway.model.JvmMetric;

@Repository
public abstract interface JvmMetricRepository
  extends JpaRepository<JvmMetric, Long>
{
  public abstract List<JvmMetric> findByName(String paramString);
  
  public abstract JvmMetric getOneById(Long paramLong);
  
  public abstract Page<JvmMetric> findAllByOrderByPingIdDesc(Pageable paramPageable);
  
  @CacheEvict
  public abstract Page<JvmMetric> findByNameOrderByPingIdDesc(String paramString, Pageable paramPageable);
}
