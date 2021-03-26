package net.whoneeds.whoneedsapi.domain.ports.projects

import net.whoneeds.whoneedsapi.domain.model.projects.Project
import net.whoneeds.whoneedsapi.domain.model.projects.ProjectRepository
import net.whoneeds.whoneedsapi.domain.model.users.UserAccountRepository
import org.springframework.stereotype.Service
import java.security.Principal


/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@Service
class ProjectService(
        private val projectRepository: ProjectRepository,
        val userRepository: UserAccountRepository
) {

    fun getAllProjects(): MutableList<Project> {
        return projectRepository.findAll()
    }

    fun createProject(project: Project, principal: Principal): Project {
        project.creator = userRepository.findByEmail(principal.name)
        return projectRepository.save(project)
    }
}
