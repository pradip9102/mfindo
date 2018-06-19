package com.vizurd.travel.mfindo.audit

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class File(
        @Id
        @GeneratedValue
        val id: Int
) : Auditable<String>() {
    var name = "UNKNOWN"
    var content = ""
}