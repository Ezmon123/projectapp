package com.cezary.projectboard.service;

import com.cezary.projectboard.domain.Backlog;
import com.cezary.projectboard.domain.Project;
import com.cezary.projectboard.domain.User;
import com.cezary.projectboard.exception.ProjectIdException;
import com.cezary.projectboard.repository.ProjectRepository;
import com.cezary.projectboard.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Log4j
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    //security
    @Autowired
    private UserRepository userRepository;


    public Project saveOrUpdateProject(Project project, String username) {
        try {
            //security
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            //
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

    public Project findByID(String projectId, String username) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project ID: " + projectId + " does not exist");
        }

        if (!project.getProjectLeader().equals(username)) {
            throw new ProjectIdException("Project not found in your account!");
        }
        return project;
    }

    public Iterable<Project> findAllPRojects(String username) {

        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username) {
//        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
//        if (project == null) {
//            throw new ProjectIdException("Project ID: " + projectId + " does not exist");
//        }
        projectRepository.delete(findByID(projectId, username));
    }
}