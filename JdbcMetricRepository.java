package com.front.ReactiveGateway.repository;

import org.springframework.data.repository.CrudRepository;

import com.front.ReactiveGateway.model.JdbcMetric;

public abstract interface JdbcMetricRepository  extends CrudRepository<JdbcMetric, Long>{
	
}
