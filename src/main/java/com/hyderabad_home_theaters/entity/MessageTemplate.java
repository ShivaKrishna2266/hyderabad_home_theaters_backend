package com.hyderabad_home_theaters.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageTemplate {

    private String id;
    private String elementName;
    private String status;
}
