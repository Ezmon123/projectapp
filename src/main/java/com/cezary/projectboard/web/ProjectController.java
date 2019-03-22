package com.cezary.projectboard.web;

import com.cezary.projectboard.domain.Project;
import com.cezary.projectboard.service.MapValidationErrorService;
import com.cezary.projectboard.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/project")
@CrossOrigin
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    /**
     * @Valid if we use this annotation, if request will be invalid method will return
     * clearer response
     * BindingResult parameter in method analyze validation of project and helps us
     * return clear response with for example errors and default message connected to them
     * <p>
     * IMPORTANT!
     * In Project class we have annotation Column with some attributes defining validation from DB side.
     * When we are calling mapValidationService method to analyze result and check if everything is OK,
     * it does not check conditions given in annotation Column.
     * <p>
     * This method is use to save new object in DB.
     * In addition it can be used to update existing object in database.
     * In order to do that we must send request with specific id(not ProjectIdentifier!)
     * and other values of object that we want to update.
     * Then JPA will first check if object with this id is in the DB and will try to update it.
     */
    @PostMapping("")
    public ResponseEntity<?> createNewProjectOrUpdateExistingOne(@Valid @RequestBody Project project, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }
        Project newProject = projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<>(newProject, HttpStatus.CREATED);
    }

    @GetMapping("/{project_id}")
    public ResponseEntity<?> findProjectByID(@PathVariable String project_id, Principal principal) {
        Project project = projectService.findByID(project_id, principal.getName());
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> findAllProjects(Principal principal) {
        return projectService.findAllPRojects(principal.getName());
    }

    @DeleteMapping("/{project_id}")
    public ResponseEntity<?> deleteProject(@PathVariable String project_id, Principal principal) {
        projectService.deleteProjectByIdentifier(project_id, principal.getName());
        return new ResponseEntity<>("Project with ID: " + project_id + " has been deleted!", HttpStatus.OK);
    }
}