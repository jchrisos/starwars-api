package com.starwars

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("com.starwars")
                .mainClass(Application.javaClass)
                .start()
    }
}