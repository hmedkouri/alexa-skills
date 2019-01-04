package io.anaxo.alexa.napster.config

import com.amazon.ask.Skill
import com.amazon.ask.attributes.persistence.PersistenceAdapter
import com.amazon.ask.builder.CustomSkillBuilder
import com.amazon.ask.dispatcher.exception.ExceptionHandler
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Value
import javax.inject.Singleton

@Singleton
@Factory
class AlexaConfig {

    @Value("\${application.id:''}")
    private val applicationId: String? = null

    @Bean
    @Singleton
    fun skill(requestHandlers: List<RequestHandler>,
              exceptionHandlers: List<ExceptionHandler>,
              persistenceAdapter: PersistenceAdapter) : Skill {
        return CustomSkillBuilder()
                .withSkillId(applicationId)
                .addExceptionHandlers(exceptionHandlers)
                .addRequestHandlers(requestHandlers)
                .withPersistenceAdapter(persistenceAdapter)
                .build()
    }
}
