package ru.gothmog.houses.service;

public interface HouseService {

    Integer countRowFromHouse();

    Integer countRowToHouse();

    String dropTable();

    String createTable();

    String createTableStatus();
}
