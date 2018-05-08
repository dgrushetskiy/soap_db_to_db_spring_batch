package ru.gothmog.houses.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.gothmog.houses.dao.HouseDao;
import ru.gothmog.houses.dao.ImplDaoFromFactory;
import ru.gothmog.houses.dao.ImplDaoToFactory;
import ru.gothmog.houses.domain.House;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HouseDaoImpl implements HouseDao {

    private static final Logger log = LoggerFactory.getLogger(HouseDaoImpl.class);
    private ImplDaoFromFactory daoFromFactory = new ImplDaoFromFactory();
    private ImplDaoToFactory daoToFactory = new ImplDaoToFactory();
    @Autowired
    @Qualifier("jdbcTemplateFrom")
    private JdbcTemplate jdbcTemplateFrom;
    @Autowired
    @Qualifier("jdbcTemplateTo")
    private JdbcTemplate jdbcTemplateTo;

    @Override
    public boolean addHouse(House house) {
        log.info("add new house");
        boolean result = false;
//        try {
//
//        }catch (){
//
//        }
        return false;
    }

    @Override
    public Integer countRowFromHouse() {
        try {
            return getInteger(daoFromFactory.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer countRowToHouse() {
        try {
            return getInteger(daoToFactory.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String dropTable() {
        log.info("drop table house");
        String SQL = "DROP TABLE IF EXISTS house";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = daoToFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return "table drop";
            }else {
                return "Table drop failed ";
            }

        } catch (SQLException|IOException ex){
            log.error("table drop failed");
            return "Table drop failed ";
        }finally {
            finallyConnect(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public String createTable() {
        log.info("create table house");
        String SQL = "CREATE TABLE public.house (unom BIGSERIAL NOT NULL,\n"
                + " ao character varying(10),\n"
                + " mr character varying(20),\n"
                + " address character varying(255),\n"
                + " kad_nom character varying(100),\n"
                + "CONSTRAINT pk_house_id PRIMARY KEY (unom))";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoToFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return "table create";
            }else {
                return "Table create failed ";
            }

        } catch (SQLException|IOException ex){
            log.error("table create failed");
            return "Table create failed ";
        }finally {
            finallyConnect(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public String createTableStatus() {
        String SQL = "CREATE TABLE public.house (unom BIGSERIAL NOT NULL,\n"
                + " ao character varying(10),\n"
                + " mr character varying(20),\n"
                + " address character varying(255),\n"
                + " kad_nom character varying(100),\n"
                + "CONSTRAINT pk_house_id PRIMARY KEY (unom))";
        try {
            jdbcTemplateTo.execute("DROP TABLE IF EXISTS house");
            System.out.println("таблица удалена");
            jdbcTemplateTo.execute(SQL);
            System.out.println("данные внесены");
            return "table created";
        }catch (Exception e) {
            return "table creation failed" + e;
        }
    }

    private Integer getInteger(Connection connection2) {
        log.info("count row table FromHouse");
        Integer count = null;
        String sql = "SELECT count(*) FROM house";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connection2;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                count = resultSet.getInt(1);
                log.info("count = " + count);
            } else {
                log.info("could not get the record counts");
            }
        } catch (SQLException ex){
            log.error("error count row FromHouse",ex);
        } finally {
            finallyConnect(connection, preparedStatement, resultSet);
        }
        return count;
    }

    private void finallyConnect(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex){
            log.error("Error close rs, ps, connect", ex);
        }
    }
}
