package ru.gothmog.houses.domain.util;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import ru.gothmog.houses.domain.House;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HousePreparedStatementSetter implements ItemPreparedStatementSetter<House> {

    public HousePreparedStatementSetter() {
    }

    @Override
    public void setValues(House house, PreparedStatement ps) throws SQLException {
        ps.setLong(1,house.getUnom());
        ps.setString(2,house.getAo());
        ps.setString(3,house.getMr());
        ps.setString(4,house.getAddress());
        ps.setString(5, house.getKadNom());
    }
}
