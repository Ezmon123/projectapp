package com.cezary.projectboard.service;

import com.cezary.projectboard.domain.Backlog;
import com.cezary.projectboard.domain.Project;
import com.cezary.projectboard.exception.ProjectIdException;
import com.cezary.projectboard.repository.ProjectRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;


    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }
            return projectRepository.save(project);
        } catch (Exception e) {
            log.info("Project with ID " + project.getProjectIdentifier().toUpperCase() + " already exist");
            throw new ProjectIdException("Project ID " + project.getProjectIdentifier().toUpperCase() + " already exist");
        }
    }

    public Project findByID(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project ID: " + projectId + " does not exist");
        }
        return project;
    }

    public Iterable<Project> findAllPRojects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project ID: " + projectId + " does not exist");
        }
        projectRepository.delete(project);
    }
}