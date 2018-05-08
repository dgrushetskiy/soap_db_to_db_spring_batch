package ru.gothmog.houses.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gothmog.houses.dao.HouseDao;
import ru.gothmog.houses.dao.impl.HouseDaoImpl;

@Configuration
public class AppConfig {
    @Bean
    public HouseDao houseDao(){
        return new HouseDaoImpl();
    }
}
