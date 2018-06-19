package com.vizurd.travel.mfindo.services

import com.google.maps.GeoApiContext
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GoogleMapsService {

    private val logger = KotlinLogging.logger { }

    @Value("\${google.map.api.key}")
    private lateinit var __google_map_api_key__: String

    private val _context: GeoApiContext by lazy {
        logger.debug { "Building a new GeoApiContext." }
        GeoApiContext.Builder().apiKey(__google_map_api_key__).build()
    }

    fun getContext(): GeoApiContext {
        return _context
    }
}