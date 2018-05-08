package ru.gothmog.houses.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatasourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public javax.sql.DataSource dataSource() throws Exception{
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix="spring.targetdatasource")
    public javax.sql.DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("jdbcTemplateFrom")
    public JdbcTemplate jdbcTemplateFrom() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    @Bean("jdbcTemplateTo")
    public JdbcTemplate jdbcTemplateTo(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(secondaryDataSource());
        return jdbcTemplate;
    }
}
