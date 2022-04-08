package com.example.oil.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WellModel {
    List<Well> wells;
}
