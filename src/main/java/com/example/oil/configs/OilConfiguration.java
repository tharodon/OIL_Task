package com.example.oil.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class OilConfiguration {

    private final static String CREATE_WELL = "CREATE TABLE well (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, name varchar(32) not null);";
    private final static String CREATE_EQUIPMENT = "create table equipment (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, name varchar(32) unique not null, id_well INTEGER not null);";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:/Users/tharodon/bot/OIL/sqlLite.db");
    }

    @Bean
    @Scope("singleton")
    public Statement getStatement() {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
        } catch (SQLException ignored) {
        }
        try {
            statement.execute(CREATE_WELL);
        } catch (SQLException ignored) {}
        try {
            statement.execute(CREATE_EQUIPMENT);
        } catch (SQLException ignored) {}
        return statement;
    }
}
