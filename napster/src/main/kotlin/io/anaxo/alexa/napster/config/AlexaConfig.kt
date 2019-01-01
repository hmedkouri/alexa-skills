package io.anaxo.alexa.napster.config

import com.amazon.ask.Skill
import com.amazon.ask.Skills
import io.anaxo.alexa.napster.handlers.*
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import javax.inject.Singleton

@Singleton
@Factory
class AlexaConfig {

    @Bean
    @Singleton
    fun skill() : Skill {
        return Skills
                .custom()
                .addRequestHandlers(
                        Exit(),
                        Fallback(),
                        Help(),
                        Launch(),
                        SessionEnded()
                )
                .build()
    }
}
