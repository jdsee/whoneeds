package net.whoneeds.whoneedsapi.adapters.api.pwReset

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

/**
 * @author Lukas Schuetz <pomcom> 2021
</pomcom> */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
internal class ResetControllerTest
@Autowired constructor(val mvc: MockMvc) {
    @Disabled
    @Test
    fun `should send Reset PW`() {
        mvc.perform(
                post("/resetPassword")
        ).andExpect {
            status().isOk
        }
    }
}