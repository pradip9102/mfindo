package com.vizurd.travel.mfindo

import com.vizurd.travel.mfindo.controllers.MapRestController
import com.vizurd.travel.mfindo.services.GoogleMapsService
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableAsync


@EnableAsync
@SpringBootApplication
@EnableJpaAuditing
@EnableAutoConfiguration(exclude = [JacksonAutoConfiguration::class])
class MFindoApplication {
    companion object {
        val logger = KotlinLogging.logger { }
    }
}

fun main(args: Array<String>) {
    runApplication<MFindoApplication>(*args)
    MFindoApplication.logger.debug { "m-Findo is up and running." }
}

/*val googleMapService = GoogleMapsService()
    val temp = MapRestController(googleMapService)
    val f = temp.getResult("bandra","goregaon","bandra","goregaon")

}
*/