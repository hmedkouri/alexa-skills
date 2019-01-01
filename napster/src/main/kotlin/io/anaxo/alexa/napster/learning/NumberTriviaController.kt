package io.anaxo.alexa.napster.learning

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import javax.inject.Inject

@Controller("/trivia")
class NumberTriviaController {

    @Inject
    lateinit var numbersService: NumbersAPIService

    @Post("/")
    fun test(year : Int) : HttpResponse<NumberTrivia> {
        val response : NumberTrivia? = numbersService?.getYearTrivia(year)
        return HttpResponse.ok(response)
    }
}
