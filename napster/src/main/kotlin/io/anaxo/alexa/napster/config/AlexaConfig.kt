package io.anaxo.alexa.napster.config

import com.amazon.ask.Skill
import com.amazon.ask.attributes.persistence.PersistenceAdapter
import com.amazon.ask.builder.CustomSkillBuilder
import com.amazon.ask.dispatcher.exception.ExceptionHandler
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import io.anaxo.alexa.napster.db.DbFactories
import io.anaxo.alexa.napster.db.DbFactory
import io.anaxo.alexa.napster.db.MapDbPersistenceAdapter
import io.anaxo.alexa.napster.utils.SpeechletRequestUtil
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Value
import javax.inject.Singleton


@Singleton
@Factory
class AlexaConfig {

    @Bean
    @Singleton
    fun skill(requestHandlers: List<RequestHandler>,
              exceptionHandlers: List<ExceptionHandler>,
              persistenceAdapter: PersistenceAdapter,
              @Value("\${application.id}") applicationId: String) : Skill {
        return CustomSkillBuilder()
                .withSkillId(applicationId)
                .addExceptionHandlers(exceptionHandlers)
                .addRequestHandlers(requestHandlers)
                .withPersistenceAdapter(persistenceAdapter)
                .build()
    }

    @Bean
    @Singleton
    fun dbFactory(@Value("\${application.db.path}") dbFilesLocationString: String?): DbFactory {
        return DbFactories.file(dbFilesLocationString!!)
    }

    @Bean
    fun mapDbPersistenceAdapter(@Value("\${application.db.path}") dbFilesLocationString: String?): MapDbPersistenceAdapter {
        return MapDbPersistenceAdapter(
                dbFactory(dbFilesLocationString),
                "sessions"
        ) { SpeechletRequestUtil.getDeviceId(it) }
    }
}
