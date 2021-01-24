package net.whoneeds.whoneedsapi.config.mail

/**
 * @author Lukas Schuetz <pomcom> 2021
 */
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.SimpleMailMessage

@Configuration
class TemplateConfig {
    @Bean
    fun resetPasswordTemplate(): SimpleMailMessage {
        val template = SimpleMailMessage()

        template.setSubject("Newsletter")
        template.setText("""
            Hello %s, 
            
            This is example newsletter message
        """.trimIndent()
        )

        return template
    }
}
