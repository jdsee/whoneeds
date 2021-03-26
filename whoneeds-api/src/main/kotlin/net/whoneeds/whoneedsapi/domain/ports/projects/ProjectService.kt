package net.whoneeds.whoneedsapi.domain.ports.projects

import net.whoneeds.whoneedsapi.domain.model.projects.Project
import net.whoneeds.whoneedsapi.domain.model.projects.ProjectRepository
import net.whoneeds.whoneedsapi.domain.model.users.UserAccount
import net.whoneeds.whoneedsapi.domain.model.users.UserAccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val userRepository: UserAccountRepository
) {

    fun getAllProjects(currentUserEmail: String): List<Project> {
        return projectRepository.findAll()
            .filter { project -> isProjectMember(project, currentUserEmail) }
    }

    fun getProject(id: Long, currentUserEmail: String): Project {
        val project = projectRepository.findByIdOrNull(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return project.takeIf { p -> isProjectMember(p, currentUserEmail) }
            ?: throw ResponseStatusException(HttpStatus.FORBIDDEN)
    }

    fun createProject(project: Project, currentUserEmail: String): Project {
        project.creator = userRepository.findByEmail(currentUserEmail)
        return projectRepository.save(project)
    }

    private fun isProjectMember(project: Project, currentUserEmail: String) =
        project.creator?.email == currentUserEmail || project.members
            .map(UserAccount::email)
            .any(currentUserEmail::equals)

    fun deleteProject(id: Long, currentUserEmail: String) =
        if (currentUserEmail == projectRepository.getOne(id).creator?.email) {
            projectRepository.deleteById(id)
        } else throw ResponseStatusException(HttpStatus.FORBIDDEN)
}
