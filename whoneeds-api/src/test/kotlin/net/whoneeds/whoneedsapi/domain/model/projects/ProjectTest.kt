package net.whoneeds.whoneedsapi.domain.model.projects

import net.whoneeds.whoneedsapi.domain.model.users.UserAccount
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester

/**
 * @author Joscha Seelig <jduesentrieb> 2021
 */
@JsonTest
class ProjectTest
@Autowired constructor(
        val jsonTester: JacksonTester<Project>
) {
    private val project = Project(
            name = "test project",
            description = "this is a test",
            creator = UserAccount(
                    id = 100,
                    email = "test@mail.com",
                    password = "pw",
                    name = "timo",
                    surname = "tester"
            ),
            members = hashSetOf(),
            address = Address(
                    name = "Gandalf",
                    street = "teststreet",
                    nr = 123,
                    city = "Mittelerde",
                    state = "Mittelerde",
                    zip = 12345
            ),
            categories = hashSetOf(SupportCategory.HUMANITARIAN_AID)
    )

    private val jsonProject = """
                {
                    "name": "test project",
                    "description": "this is a test",
                    "members": [],
                    "address": {
                       "name": "Gandalf",
                       "street": "teststreet",
                       "nr": 123,
                       "city": "Mittelerde",
                       "state": "Mittelerde",
                       "zip": 12345
                    },
                    "categories": ["humanitarian"]
                }
            """.trimIndent()

    @Test
    fun `should serialize project properly`() {
        val serialized = jsonTester.write(project)

        assertThat(serialized).isEqualToJson(jsonProject)
    }

    @Test
    fun `should deserialize json project properly`() {
        val deserialized = jsonTester.parseObject(jsonProject)

        assertThat(deserialized)
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(project)
    }
}
