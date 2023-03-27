package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheReset {

	@Autowired
	CacheManager cacheManager;

	@Scheduled(fixedDelay = 3600000)
	public void reportCurrentTime() {
		cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());
		System.out.println("cache reset");
	}

}
