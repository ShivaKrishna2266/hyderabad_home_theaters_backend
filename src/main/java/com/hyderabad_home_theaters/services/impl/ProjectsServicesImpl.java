package com.hyderabad_home_theaters.services.impl;

import com.hyderabad_home_theaters.DTOs.ProjectsDTO;
import com.hyderabad_home_theaters.entity.Projects;
import com.hyderabad_home_theaters.exception.ApplicationBusinessException;
import com.hyderabad_home_theaters.mapper.ProjectsMapper;
import com.hyderabad_home_theaters.repository.ProjectsRepository;
import com.hyderabad_home_theaters.services.ProjectsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ProjectsServicesImpl implements ProjectsServices {

    @Autowired
    private ProjectsRepository projectsRepository;


    @Override
    public ProjectsDTO getProjectById(Long projectId) {
        Optional<Projects> optionalProjects = projectsRepository.findById(projectId);

        return optionalProjects.map(ProjectsMapper::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));
    }


    @Override
    public List<ProjectsDTO> getAllProjects() {
        List<Projects> projects = projectsRepository.findAll();
        return projects
                .stream()
                .map(ProjectsMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectsDTO createProject(ProjectsDTO projectsDTO) {
        try {
            Projects projects = ProjectsMapper.convertToEntity(projectsDTO );
            projects.setProjectName(projectsDTO.getProjectName());
            projects.setCustomerName(projectsDTO.getCustomerName());
            projects.setDescription(projectsDTO.getDescription());
            projects.setImages(projectsDTO.getImages());
            projects.setStartDate(Date.valueOf(LocalDate.now()));
            projects.setEndDate(Date.valueOf(LocalDate.now()));
            projects.setCreatedBy("System");
            projects.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            projects.setUpdatedBy("System");
            projects.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

            Projects savedProjects = projectsRepository.save(projects);
            ProjectsDTO projectsDTO1 = ProjectsMapper.convertToDTO(savedProjects);
            return  projectsDTO1;
        }catch (Exception e){
            throw new ApplicationBusinessException("Business error occurred", e);
        }
    }

    @Override
    public ProjectsDTO updateProject(Long projectId, ProjectsDTO projectsDTO) {
        Optional<Projects> optionalProjects = projectsRepository.findById(projectId);
        if (optionalProjects.isPresent()){
            Projects projects = optionalProjects.get();
            projects.setProjectName(projectsDTO.getProjectName());
            projects.setCustomerName(projectsDTO.getCustomerName());
            projects.setDescription(projectsDTO.getDescription());
            projects.setImages(projectsDTO.getImages());
            projects.setStartDate(Date.valueOf(LocalDate.now()));
            projects.setEndDate(Date.valueOf(LocalDate.now()));
            projects.setUpdatedBy("System");
            projects.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

            Projects savedProjects =  projectsRepository.save(projects);
            ProjectsDTO projectsDTO1 = ProjectsMapper.convertToDTO(savedProjects);
            return  projectsDTO1;

        }
        return null;
    }

    @Override
    public void deleteProjectById(Long projectId) {
        Optional<Projects> optionalProjects = projectsRepository.findById(projectId);
        if (optionalProjects.isPresent()) {
            projectsRepository.deleteById(projectId);
        }
    }
}
