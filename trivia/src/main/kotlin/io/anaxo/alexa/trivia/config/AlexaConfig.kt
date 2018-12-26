package io.anaxo.alexa.trivia.config

import com.amazon.speech.speechlet.servlet.SpeechletServlet
import io.anaxo.alexa.trivia.handlers.HandlerSpeechlet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AlexaConfig {

    @Autowired
    private val handlerSpeechlet: HandlerSpeechlet? = null

    @Bean
    fun registerSpeechletServlet(): ServletRegistrationBean<*> {

        // force system property to no validate signed request before we create the speechlet
        System.setProperty("com.amazon.speech.speechlet.servlet.disableRequestSignatureCheck", "true")

        val speechletServlet = SpeechletServlet()
        speechletServlet.setSpeechlet(handlerSpeechlet)

        return ServletRegistrationBean(speechletServlet, "/alexa")
    }
}
