package net.whoneeds.whoneedsapi.domain.ports

import net.whoneeds.whoneedsapi.domain.model.UserSettings
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "settings", path = "settings")
interface UserSettingsRepository : PagingAndSortingRepository<UserSettings, Long>