package com.vizurd.travel.mfindo.utils

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder

class GsonUtil {
    companion object {
        val gsonBuilder = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)

        fun <T> plainJson(obj: T): String? {
            return gsonBuilder.create().toJson(obj)
        }

        fun <T> prettyJson(obj: T): String? {
            return gsonBuilder.setPrettyPrinting().create().toJson(obj)
        }

        inline fun <reified T> jsonToObject(json: String): T? {
            return gsonBuilder.create().fromJson(json, T::class.java)
        }
    }
}