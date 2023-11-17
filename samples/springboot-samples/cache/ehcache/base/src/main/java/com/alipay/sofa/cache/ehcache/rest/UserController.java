package com.alipay.sofa.cache.ehcache.rest;

import net.sf.ehcache.management.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;

@CacheConfig(cacheNames = "base")
@RestController
public class UserController {

    @Autowired
    private ApplicationContext applicationContext;

    @Cacheable(key = "#id")
    @PostMapping("/user")
    public String getUserById(@RequestParam String id) {
        return "user_" + id;
    }

    @PostConstruct
    public void registerMBean() {
        EhCacheCacheManager ehCacheManager = (EhCacheCacheManager) applicationContext.getBean("baseEhcacheCacheManager");
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ManagementService.registerMBeans(ehCacheManager.getCacheManager(), mBeanServer, true, true, true, true);
    }
}
