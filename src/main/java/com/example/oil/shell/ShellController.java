package com.example.oil.shell;

import com.example.oil.Logic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.xml.bind.JAXBException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@ShellComponent
public class ShellController {

    Logic logic;

    @Autowired
    public ShellController(Logic logic) {
        this.logic = logic;
    }

    @ShellMethod("create")
    public String create() throws SQLException {
        return logic.createWell();
    }

    @ShellMethod("show")
    public String show(@ShellOption String input) throws SQLException {
        return logic.show(input);
    }

    @ShellMethod("xml")
    public String export(@ShellOption String fileName) throws JAXBException, SQLException {
        return logic.xmlMarshaller(fileName);
    }
}
