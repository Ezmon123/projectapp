package com.cezary.projectboard.service;

import com.cezary.projectboard.domain.Backlog;
import com.cezary.projectboard.domain.Project;
import com.cezary.projectboard.domain.ProjectTask;
import com.cezary.projectboard.exception.ProjectIdException;
import com.cezary.projectboard.repository.BacklogRepository;
import com.cezary.projectboard.repository.ProjectTaskRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        try {
            //Exceptions : Project not found
            //PTs to be added to a specific project, project !=null, BL exist
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            //Set the backlog to project
            projectTask.setBacklog(backlog);
            //We want out project sequence to be like : IDPRO-1, IDPRO-2, ....100, 101
            Integer backlogSequence = backlog.getPTSeqeunce();
            //Update the backlog sequence
            backlogSequence++;

            backlog.setPTSeqeunce(backlogSequence);
            //Add sequqnce to ProjectTask
            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
            //INITIAL priority when priority is null
            if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
                projectTask.setPriority(3);
            }
            //INITIAL status when status is null
            if (projectTask.getStatus() == null || projectTask.getStatus().isEmpty()) {
                projectTask.setStatus("TO-DO");
            }
        } catch (Exception e) {
            throw new ProjectIdException("Project with ID: " + projectIdentifier + " does not exist");
        }
        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findAllById(String projectIdentifier) {
        List<ProjectTask> projectTasks = projectTaskRepository.findAllByProjectIdentifierOrderByPriority(projectIdentifier);
        if (projectTasks.isEmpty()) {
            throw new ProjectIdException("Project with ID: " + projectIdentifier + " does not exist");
        }
        return projectTasks;
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
        //make sure that we are searching on existing project
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if (backlog == null) {
            throw new ProjectIdException("Project with ID: " + backlog_id + " does not exist");
        }
        //make sure that our task exist
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if (projectTask == null) {
            throw new ProjectIdException("Project Task with project sequence: " + pt_id + " not found");
        }
        //make sure that th backlog/project id int the path correspond to the right project
        if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectIdException("Project Task with project sequence: " + pt_id + " does not exist in Project with id: " + backlog_id);
        }
        return projectTaskRepository.findByProjectSequence(pt_id);
    }

    public ProjectTask updateByProjectSequence(ProjectTask updateTask, String backlog_id, String pt_id) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);

        projectTask = updateTask;

        return projectTaskRepository.save(projectTask);
    }

    public void deleteProjectTaskByProjectSequence(String backlog_id, String pt_id){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);

        projectTaskRepository.delete(projectTask);
    }





}
