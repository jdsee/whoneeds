package net.whoneeds.whoneedsapi.adapters.api.projects

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.PROJECTS_ROUTE
import net.whoneeds.whoneedsapi.domain.model.projects.Project
import net.whoneeds.whoneedsapi.domain.ports.projects.ProjectService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.security.Principal


/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@RestController
@RequestMapping(PROJECTS_ROUTE)
class ProjectController(
    private val projectService: ProjectService
) {
    @GetMapping
    fun getAllProjects(principal: Principal): List<Project> =
        projectService.getAllProjects(principal)

    @GetMapping("{id}")
    fun getProject(@PathVariable("id") id: Long, principal: Principal) =
        projectService.getProject(id, principal)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createProject(
        @RequestBody project: Project, principal: Principal, uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<Void> {
        val id = projectService.createProject(project, principal).id
        val uriComponents = uriComponentsBuilder
            .path("$PROJECTS_ROUTE/{id}")
            .buildAndExpand(id)
        return ResponseEntity.created(uriComponents.toUri()).build()
    }
}
