package com.example.oil.repository;

import com.example.oil.entity.Equipment;
import com.example.oil.entity.Well;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class WellRepository {

    private final Statement statement;

    @Autowired
    public WellRepository(Statement statement) {
        this.statement = statement;
    }

    public void save (String nameOfWell) throws SQLException {
        statement.execute("INSERT INTO well (name) VALUES ('" + nameOfWell + "');");
    }

    public Well findByName(String name) throws SQLException {
        statement.execute("select id, name from well where name='" + name + "';");
        ResultSet resultSet = statement.getResultSet();
        if (resultSet.next()){
            return new Well(resultSet.getLong("id"), resultSet.getString("name"));
        }
        return null;
    }

    public List<Well> findAll() throws SQLException {
        statement.execute("select well.id, well.name, equipment.id, equipment.name from well inner join equipment on well.id = equipment.id_well order by well.id;");
        ResultSet resultSet = statement.getResultSet();
        return getWells(resultSet);
    }

    private List<Well> getWells(ResultSet resultSet) throws SQLException {
        List<Well> wells = new ArrayList<>();
        List<Equipment> equipment = new ArrayList<>();
        try {
            while (resultSet.next()) {
                equipment.add(new Equipment(resultSet.getLong(3), resultSet.getString(4), resultSet.getLong(1)));
                wells.add(new Well(resultSet.getLong(1), resultSet.getString(2)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        wells = wells.stream().distinct().collect(Collectors.toList());
        int j = 0;
        for (int i = 0; i < equipment.size(); i++){
            if (!equipment.get(i).getId_well().equals(wells.get(j).getId())){
                j++;
                continue;
            }
            wells.get(j).getEquipments().add(equipment.get(i));
        }
        return wells;
    }

    public Long getInfo(String name) throws SQLException {
        String query = String.format("SELECT well.name, COUNT(*) FROM well INNER JOIN equipment ON equipment.id_well = well.id where well.name='%s';", name);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet resultSet = statement.getResultSet();
        if (resultSet.next()){
            return resultSet.getLong(2);
        }
        throw new RuntimeException("Такого имени не существует");
    }
}
