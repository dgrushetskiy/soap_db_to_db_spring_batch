package ru.gothmog.houses.dao;

import org.springframework.stereotype.Repository;
import ru.gothmog.houses.domain.House;

public interface HouseDao {

    boolean addHouse(House house);

    Integer countRowFromHouse();

    Integer countRowToHouse();

    String dropTable();

    String createTable();

    String createTableStatus();
}
