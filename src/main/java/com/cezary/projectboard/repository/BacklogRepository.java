package com.cezary.projectboard.repository;

import com.cezary.projectboard.domain.Backlog;
import org.springframework.data.repository.CrudRepository;

public interface BacklogRepository extends CrudRepository<Backlog, Long> {
    Backlog findByProjectIdentifier(String projectIdentifier);
}
