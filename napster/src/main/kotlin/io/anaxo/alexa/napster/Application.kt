package io.anaxo.alexa.napster

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("io.anaxo.alexa.napster")
                .mainClass(Application.javaClass)
                .start()
    }
}
