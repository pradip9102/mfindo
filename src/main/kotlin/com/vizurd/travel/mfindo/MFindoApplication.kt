package com.vizurd.travel.mfindo

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MFindoApplication {
    companion object {
        val logger = KotlinLogging.logger { }
    }

}

fun main(args: Array<String>) {
    runApplication<MFindoApplication>(*args)
    MFindoApplication.logger.debug { "m-Findo is up and running." }
    val stepConvertor = StepConvertor()
//    stepConvertor.fetchFromApi()
    stepConvertor.fetchFromGeoApi()
}