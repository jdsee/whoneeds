package net.whoneeds.whoneedsapi.domain.model.projects

import org.springframework.data.jpa.repository.JpaRepository


/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
interface ProjectRepository : JpaRepository<Project, Long>
