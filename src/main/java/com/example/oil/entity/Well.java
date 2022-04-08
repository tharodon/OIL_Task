package com.example.oil.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@XmlRootElement
public class Well {
    Long id;
    String name;

    Set<Equipment> equipments;

    public Well(Long id, String name) {
        this.id = id;
        this.name = name;
        equipments = new HashSet<>();
    }
}
