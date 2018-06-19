package com.vizurd.travel.mfindo.audit

enum class Action(private val type: String) {

    INSERTED("INSERTED"),
    UPDATED("UPDATED"),
    DELETED("DELETED");

    fun value(): String {
        return this.type
    }

    override fun toString(): String {
        return type
    }
}