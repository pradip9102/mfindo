package com.vizurd.travel.mfindo.audit

import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import java.util.*

class AuditorAwareImpl : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        //("Use spring security to return currently logged in user")
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            return Optional.empty()
        }
        return Optional.ofNullable((authentication.principal as User).username)
    }
}