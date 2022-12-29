package com.repository.finance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Shop;
import com.model.finance.DataActivity;

public interface DataActivityRepository extends JpaRepository<DataActivity, Long> {

	@Query(value = "select s from DataActivity da, Shop s, Activity a where da.shop.id = s.id and da.activity.id = a.id and a.id in (:activityIds)")
    List<Shop> getShopsByActivity(@Param(value = "activityIds") List<Long> ids);
}
