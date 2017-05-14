package by.bsu.chemistry.configuration;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ivan on 12.05.2017.
 */
@Configuration
@EnableCaching
@ComponentScan("by.bsu.chemistry")
public class AppConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(Arrays.asList(cacheManagerFactory().getObject()));
        return manager;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean cacheManagerFactory() {
        ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
        cacheFactoryBean.setName("Panes");
        return cacheFactoryBean;
    }

}
