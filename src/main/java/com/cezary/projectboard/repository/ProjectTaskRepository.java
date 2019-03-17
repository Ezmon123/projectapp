package com.cezary.projectboard.repository;

import com.cezary.projectboard.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
    List<ProjectTask> findAllByProjectIdentifierOrderByPriority(String projectIdentifier);

    ProjectTask findByProjectSequence(String projectSequence);
}
