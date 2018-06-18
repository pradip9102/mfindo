package com.vizurd.travel.mfindo

import com.google.gson.GsonBuilder
import com.google.maps.DirectionsApi
import com.google.maps.model.DirectionsStep
import com.google.maps.model.TravelMode
import com.vizurd.travel.mfindo.models.CommonStepUser
import com.vizurd.travel.mfindo.models.UserStep
import mu.KotlinLogging
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class StepConvertor {

    val logger = KotlinLogging.logger { }

    fun fetchFromGeoApi() {
//        val gson = GsonBuilder().setPrettyPrinting().create()
        val context = RetrofitProvider().getGeoApiContext()
        val directionRequest1 = DirectionsApi.newRequest(context)
                .origin("bandra")
                .destination("borivali")
                .mode(TravelMode.DRIVING)
        val directionRequest2 = DirectionsApi.newRequest(context)
                .origin("ghatkopar")
                .destination("borivali")
                .mode(TravelMode.DRIVING)
        val result1 = directionRequest1.await()
        val result2 = directionRequest2.await()
        val userSteps1 = result1.routes[0].legs[0].steps
        val userSteps2 = result2.routes[0].legs[0].steps
        findCommonPath(
                "A",
                userSteps1,
                "B",
                userSteps2
        )

    }

    /*
    *
    * */
    private fun findCommonPath(
            user1: String,
            steps1: Array<DirectionsStep>,
            user2: String,
            steps2: Array<DirectionsStep>
    ) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val usersSteps = ArrayList<UserStep>()
        steps1.forEach { usersSteps.add(UserStep(it, user1)) }
        steps2.forEach { usersSteps.add(UserStep(it, user2)) }
        val step3 = Arrays.copyOf(steps1, steps1.size + steps2.size)
        System.arraycopy(steps2, 0, step3, steps1.size, steps2.size)
        val stepList = step3.asList().distinctBy { Pair(it.startLocation, it.endLocation) }
        var commonStepUser = ArrayList<CommonStepUser>()
        logger.debug { "Step1 size -> ${steps1.size}" }
        logger.debug { "Step2 size -> ${steps2.size}" }
        logger.debug { "Step3 size -> ${step3.size}" }
        logger.debug { "stepList size -> ${stepList.size}" }

        printLatLong(steps1, fileName = "step1.txt")
        printLatLong(steps2, fileName = "step2.txt")
        stepList.forEach { step ->
            val commonnSteps = usersSteps.filter { uStep ->
                (uStep.step!!.startLocation!!.equals(step.startLocation)
                        && (uStep.step!!.endLocation!!.equals(step.endLocation)))
            }
            logger.debug { "common steps size -> ${commonnSteps.size}" }
//            println(gson.toJson(commonnSteps))
            val users = ArrayList<String>()
            val commonUserStep = CommonStepUser(step, users)
            commonnSteps.forEach {
                commonUserStep.users.add(it.users)
            }
            commonStepUser.add(commonUserStep)
        }
        commonStepUser = commonStepUser.filter { stUser -> stUser.users.size > 1 } as ArrayList
        logger.debug { "commonStepUser size -> ${commonStepUser.size}" }
        println(gson.toJson(commonStepUser))
    }

    fun printLatLong(steps: Array<DirectionsStep>, fileName: String) {
        File(fileName).printWriter().use { out ->
            steps.forEach {
                out.println("start location: [${it.startLocation.lat}, ${it.startLocation.lng}]," +
                        "end location: [${it.endLocation.lat}, ${it.endLocation.lng}]")
            }
        }
    }
}