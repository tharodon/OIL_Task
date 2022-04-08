package com.example.oil;

import com.example.oil.entity.Equipment;
import com.example.oil.entity.Well;
import com.example.oil.entity.WellModel;
import com.example.oil.repository.EquipmentRepository;
import com.example.oil.repository.WellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.sql.SQLException;
import java.util.*;

@Component
public class Logic {

    public final static String WELL = "well";
    public final static String EQUIPMENT = "equipment";
    public static Long koef = 0L;

    WellRepository wellRepository;
    EquipmentRepository equipmentRepository;

    @Autowired
    public Logic(WellRepository wellRepository, EquipmentRepository equipmentRepository) {
        this.wellRepository = wellRepository;
        this.equipmentRepository = equipmentRepository;
        try{
            koef = equipmentRepository.getMaxId();
        }catch (SQLException ignore){
        }
    }

    public String createWell() throws SQLException {
        System.out.println("Введите количество единиц оборудования: ");
        Scanner scanner = new Scanner(System.in);
        long countOfEquipments = -1;
        while (countOfEquipments < 0) {
            String input = scanner.nextLine();
            countOfEquipments = Long.parseLong(input);
            if (countOfEquipments < 0) {
                System.out.println("Число не должно быть отрицательным\nВведите количество единиц оборудования: ");
            }
        }
        System.out.println("Введите имя скважины");
        String input = "";
        while (true) {
            input = scanner.nextLine();
            try {
                wellRepository.save(input);
                break;
            } catch (SQLException e) {
                System.out.println("Имя уже существует\nВведите имя скважины: ");
            }
        }
        Well well = wellRepository.findByName(input);
        Long idOfWell = well.getId();
        while (countOfEquipments-- > 0){
            equipmentRepository.save(new Equipment(well.getName(), idOfWell, koef++));
        }
        return "OK";
    }

    public String show (String input) throws SQLException {
        Long count = wellRepository.getInfo(input);
        if (count < 1){
            return "Такого имени не существует";
        }
        return "Название: " + input + " | Количество единиц оборудования: " + count + "\n";
    }

    public String xmlMarshaller(String file) throws JAXBException, SQLException {
        List<Well> well = wellRepository.findAll();
        marshalling(file, well);
        return "OK";

    }

    private void marshalling(String file, List<Well> well) throws JAXBException {
        WellModel wellModel = new WellModel(well);
        JAXBContext context = JAXBContext.newInstance(WellModel.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        File xml = new File(file + ".xml");
        try{
            marshaller.marshal(wellModel, xml);
        }catch (JAXBException e){
            e.printStackTrace();
        }
    }
}
