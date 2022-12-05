package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.finance.DataActivity;

public interface DataActivityRepository extends JpaRepository<DataActivity, Long> {

}
