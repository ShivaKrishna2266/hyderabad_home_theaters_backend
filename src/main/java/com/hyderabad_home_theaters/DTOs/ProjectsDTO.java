package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectsDTO {

    private Long projectId;
    private String projectName;
    private String customerName;
    private String description;
    private List<String> images;
    private Date startDate;
    private Date endDate;
}
