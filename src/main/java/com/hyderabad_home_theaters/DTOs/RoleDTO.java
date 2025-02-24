package com.hyderabad_home_theaters.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private Long roleId;
    private String roleName;
    private String roleDescription;
    private String createdBy;
    private Timestamp createdDate;
    private String updatedBy;
    private  Timestamp updatedDate;
}
