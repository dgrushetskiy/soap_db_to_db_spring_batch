package ru.gothmog.houses.batch.steps;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gothmog.houses.domain.House;
import ru.gothmog.houses.domain.util.HousePreparedStatementSetter;
import ru.gothmog.houses.domain.util.HouseRowMapper;

import javax.sql.DataSource;

@Configuration
public class HouseBatchStep {

    /**
     *This is is to read data from input datasource
     */
    @Autowired
    JobRepository jobRepository;
    @Bean
    public ItemReader jdbcReader(DataSource dataSource) {

        JdbcCursorItemReader<House> jdbcCursorItemReader = new JdbcCursorItemReader<House>();
        jdbcCursorItemReader.setDataSource(dataSource);
        //  System.out.println("dataSource: "+dataSource.toString());
        jdbcCursorItemReader.setSql(" SELECT unom,ao,mr,address,kad_nom FROM house ");
        jdbcCursorItemReader.setVerifyCursorPosition(false);
        jdbcCursorItemReader.setRowMapper(new HouseRowMapper());
        return jdbcCursorItemReader;
    }

    /**
     *This is is to insert data into target datasource
     */
    @Bean
    @Transactional(isolation = Isolation.DEFAULT)
    public JdbcBatchItemWriter<House> jdbcWriter(DataSource targetdatasource) {

        String QUERY_INSERT =" INSERT INTO house(unom, ao, mr, address, kad_nom) VALUES (?, ?, ?, ?, ?) ";
        JdbcBatchItemWriter<House> databaseItemWriter = new JdbcBatchItemWriter<House>();
        databaseItemWriter.setDataSource(targetdatasource);
        //System.out.println("dataSource: "+targetdatasource.toString());
        databaseItemWriter.setSql(QUERY_INSERT);
        ItemPreparedStatementSetter<House> valueSetter =
                new HousePreparedStatementSetter();
        databaseItemWriter.setItemPreparedStatementSetter(valueSetter);
        return databaseItemWriter;
    }
    @Bean
    public ItemReader jdbcCountReader(DataSource dataSource){
        JdbcCursorItemReader<House> jdbcCursorItemReader = new JdbcCursorItemReader<House>();
        jdbcCursorItemReader.setDataSource(dataSource);
        //  System.out.println("dataSource: "+dataSource.toString());
        jdbcCursorItemReader.setSql(" SELECT count(*) FROM house ");
        jdbcCursorItemReader.setVerifyCursorPosition(false);
        jdbcCursorItemReader.setRowMapper(new HouseRowMapper());
        return jdbcCursorItemReader;
    }
}
