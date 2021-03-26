package net.whoneeds.whoneedsapi.domain.ports.projects

import net.whoneeds.whoneedsapi.domain.model.projects.Project
import net.whoneeds.whoneedsapi.domain.model.projects.ProjectRepository
import net.whoneeds.whoneedsapi.domain.model.users.UserAccount
import net.whoneeds.whoneedsapi.domain.model.users.UserAccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.security.Principal


/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val userRepository: UserAccountRepository
) {

    fun getAllProjects(principal: Principal): List<Project> {
        return projectRepository.findAll()
            .filter { project -> isProjectMember(project, principal) }
    }

    fun getProject(id: Long, principal: Principal): Project {
        val project = projectRepository.findByIdOrNull(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return project.takeIf { p -> isProjectMember(p, principal) }
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }

    fun createProject(project: Project, principal: Principal): Project {
        project.creator = userRepository.findByEmail(principal.name)
//        project.creator?.let { project.members.add(it) }
        return projectRepository.save(project)
    }

    private fun isProjectMember(project: Project, principal: Principal) =
        project.creator?.email == principal.name || project.members
            .map(UserAccount::email)
            .any(principal.name::equals)
}
