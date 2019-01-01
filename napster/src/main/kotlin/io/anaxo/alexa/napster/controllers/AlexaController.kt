package io.anaxo.alexa.napster.controllers

import com.amazon.ask.Skill
import com.amazon.ask.model.RequestEnvelope
import com.amazon.ask.model.ResponseEnvelope
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Inject

@Controller("/napster")
class AlexaController {

    val log : Logger = LoggerFactory.getLogger(AlexaController::class.java)

    @Inject
    lateinit var skill: Skill

    @Post("/")
    fun alexa(@Body request: RequestEnvelope) : HttpResponse<ResponseEnvelope>{
        log.info("{}", request)
        val response : ResponseEnvelope = skill.invoke(request)
        return HttpResponse.ok(response)
    }


}
