package com.hyderabad_home_theaters.services;

import com.hyderabad_home_theaters.DTOs.ProjectsDTO;

import java.util.List;

public interface ProjectsServices {

    ProjectsDTO getProjectById(Long projectId);

    List<ProjectsDTO> getAllProjects();

    ProjectsDTO createProject (ProjectsDTO projectsDTO);

    ProjectsDTO updateProject (Long projectId, ProjectsDTO projectsDTO);

    void deleteProjectById(Long projectId);
}
