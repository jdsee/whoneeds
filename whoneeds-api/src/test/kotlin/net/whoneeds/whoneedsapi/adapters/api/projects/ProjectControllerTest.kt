package net.whoneeds.whoneedsapi.adapters.api.projects

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.PROJECTS_ROUTE
import net.whoneeds.whoneedsapi.domain.model.projects.ProjectRepository
import net.whoneeds.whoneedsapi.domain.model.users.UserAccount
import net.whoneeds.whoneedsapi.domain.model.users.UserAccountRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

/**
 * @author Joscha Seelig <jduesentrieb> 2021
 **/
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "creator@mail.com")
class ProjectControllerTest
@Autowired constructor(
    private val mvc: MockMvc,
    private val projectRepository: ProjectRepository,
    private val userRepository: UserAccountRepository
) {
    @BeforeEach
    fun setUp() {
        userRepository.save(
            UserAccount(
                email = "creator@mail.com",
                password = "password",
                name = "Alan",
                surname = "Turing"
            )
        )
    }

    @Test
    fun `should save new project when request is valid`() {
        doCreateProjectRequest().andExpect {
            status { isCreated() }
            header { exists(HttpHeaders.LOCATION) }
            assertThat(projectRepository.findAll()[0].name).isEqualTo("Berliner Tafel")
        }
    }

    @Test
    fun `should not save new project if name is already present`() {
        doCreateProjectRequest()
            .andExpect {
                status { isCreated() }
            }
        doCreateProjectRequest()
            .andExpect {
                status { isConflict() }
                projectRepository.flush()
                assertThat(projectRepository.count()).isOne()
            }
    }

    private fun doCreateProjectRequest() = mvc.post(PROJECTS_ROUTE) {
        contentType = MediaType.APPLICATION_JSON
        content = """
            {
                "name": "Berliner Tafel",
                "description": "Seit 1993 sammelt die Berliner Tafel e.V. Lebensmittelspenden in ganz Berlin ein und bringt die Lebensmittel zu denen, die diese brauchen.",
                "categories": ["refugees"],
                "members": [],
                "address": {
                    "name": "Gandalf",
                    "street": "teststreet",
                    "nr": 123,
                    "city": "Mittelerde",
                    "state": "Mittelerde",
                    "zip": 12345
                }
            }""".trimIndent()
    }
}
