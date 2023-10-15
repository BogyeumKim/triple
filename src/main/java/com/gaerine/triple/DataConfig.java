package com.gaerine.triple;


import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class DataConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.local.hikari")
    public DataSource localDataSource(){
        HikariDataSource build = DataSourceBuilder.create().type(HikariDataSource.class).build();
        return build;
    }

    @Bean
    @ConfigurationProperties("spring.datasource.cloude.hikari")
    public DataSource cloudeDataSource(){
        HikariDataSource build = DataSourceBuilder.create().type(HikariDataSource.class).build();
        return build;
    }

    @Bean
    @DependsOn({"localDataSource","cloudeDataSource"})
    public DataSource routingDataSource(
            @Qualifier("localDataSource") DataSource localDataSource,
            @Qualifier("cloudeDataSource") DataSource cloudeDataSource) {

        RoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> map = new HashMap<>() {};
        map.put("local",localDataSource);
        map.put("cloude",cloudeDataSource);

        routingDataSource.setTargetDataSources(map);
        routingDataSource.setDefaultTargetDataSource(cloudeDataSource);

        return routingDataSource;
    }


    @Bean
    @Primary
    public LazyConnectionDataSourceProxy dataSource(DataSource routingDataSource){
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    public MybatisInterceptor interceptor() {
        return new MybatisInterceptor();
    }


}
