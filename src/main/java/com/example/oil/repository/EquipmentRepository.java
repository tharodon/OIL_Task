package com.example.oil.repository;

import com.example.oil.entity.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class EquipmentRepository {

    private final Statement statement;

    @Autowired
    public EquipmentRepository(Statement statement) {
        this.statement = statement;
    }

    public void save(Equipment equipment) throws SQLException {
        String query = String.format("insert into equipment (name, id_well) values ('%s', %s);", equipment.getName(), equipment.getId_well().toString());
        try {
            statement.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Long getMaxId() throws SQLException {
        statement.execute("select max(id) as maximal from equipments;");
        ResultSet resultSet = statement.getResultSet();
        if (resultSet.next()){
            return resultSet.getLong("maximal");
        }
        return 0L;
    }
}
