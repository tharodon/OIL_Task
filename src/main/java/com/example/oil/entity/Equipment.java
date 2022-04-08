package com.example.oil.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Equipment {
    Long id;
    String name;
    Long id_well;

    public Equipment(String nameOfWell, Long well, Long maxId) {
        this.name = nameOfWell + "___" + maxId;
        this.id_well = well;
    }

    public Equipment(Long id, String name, Long id_well) {
        this.id = id;
        this.name = name;
        this.id_well = id_well;
    }
}
