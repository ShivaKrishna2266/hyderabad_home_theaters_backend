package com.hyderabad_home_theaters.mapper;

import com.hyderabad_home_theaters.DTOs.ProjectsDTO;
import com.hyderabad_home_theaters.entity.Projects;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

public class ProjectsMapper {

    public static final ModelMapper modelMapper = new ModelMapper();

    public static ProjectsDTO convertToDTO(Projects projects) {
        ProjectsDTO dto = new ProjectsDTO();
        BeanUtils.copyProperties(projects, dto);
        return dto;
    }

    public static Projects convertToEntity(ProjectsDTO projectsDTO) {
        Projects otp = new Projects();
        BeanUtils.copyProperties(projectsDTO,otp);
        return otp;
    }
}
