package net.fluxcapdata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;

@SpringBootApplication
public class ComicExplorerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComicExplorerApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

    @Configuration
    @EnableCaching
    class CachingConfig {

        @Bean
        public CacheManager getEhCacheManager() {
            return new EhCacheCacheManager(getEhCacheFactory().getObject());
        }

        @Bean
        public EhCacheManagerFactoryBean getEhCacheFactory() {
            EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
            factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
            factoryBean.setShared(true);
            return factoryBean;
        }
    }

}