package com.vizurd.travel.mfindo.audit

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
class JpaConfig {

    @Bean
    fun auditorAware(): AuditorAware<String> {
        return AuditorAwareImpl()
    }
}