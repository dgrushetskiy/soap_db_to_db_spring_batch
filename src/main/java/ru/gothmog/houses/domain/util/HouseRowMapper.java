package ru.gothmog.houses.domain.util;

import org.springframework.jdbc.core.RowMapper;
import ru.gothmog.houses.domain.House;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HouseRowMapper implements RowMapper<House> {
    @Override
    public House mapRow(ResultSet rs, int rowNum) throws SQLException {
        House house = new House();
        house.setUnom(rs.getLong("unom"));
        house.setAo(rs.getString("ao"));
        house.setMr(rs.getString("mr"));
        house.setAddress(rs.getString("address"));
        house.setKadNom(rs.getString("kad_nom"));
        return house;
    }
}
