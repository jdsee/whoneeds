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

        template.setSubject("Whoneeds Password Reset")
        template.setText("""
            Hello %s, 
            
            You requested for a password reset, please use the password reset link.
        """.trimIndent()
        )

        return template
    }
}
