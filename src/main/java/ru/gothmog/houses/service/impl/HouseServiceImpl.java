package ru.gothmog.houses.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.gothmog.houses.dao.HouseDao;
import ru.gothmog.houses.service.HouseService;

@Service("houseService")
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseDao houseDao;

    @Override
    public Integer countRowFromHouse() {
        return houseDao.countRowFromHouse();
    }

    @Override
    public Integer countRowToHouse() {
        return houseDao.countRowToHouse();
    }

    @Override
    public String dropTable() {
        return houseDao.dropTable();
    }

    @Override
    public String createTable() {
        return houseDao.createTable();
    }

    @Override
    public String createTableStatus() {
        return houseDao.createTableStatus();
    }
}
