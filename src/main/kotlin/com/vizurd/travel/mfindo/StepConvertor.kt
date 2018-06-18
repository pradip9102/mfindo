package com.vizurd.travel.mfindo

import com.google.gson.GsonBuilder
import com.google.maps.DirectionsApi
import com.google.maps.model.DirectionsStep
import com.google.maps.model.TravelMode
import com.vizurd.travel.mfindo.models.CommonStepUser
import com.vizurd.travel.mfindo.models.UserStep
import java.util.*


class StepConvertor {

    fun fetchFromGeoApi() {
//        val gson = GsonBuilder().setPrettyPrinting().create()
        val context = RetrofitProvider().getGeoApiContext()
        val directionRequest1 = DirectionsApi.newRequest(context)
                .origin("Kurla")
                .destination("ville parle")
                .mode(TravelMode.DRIVING)
        val directionRequest2 = DirectionsApi.newRequest(context)
                .origin("Kurla")
                .destination("ville parle")
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

//        val step3 =
        val commonStepUser = ArrayList<CommonStepUser>()
        steps1.forEach { step ->
            val commonnSteps = usersSteps.filter { uStep -> uStep.step!!.equals(step) }
            val users = ArrayList<String>()
            val commonUserStep = CommonStepUser(step, users)
            commonnSteps.forEach {
                commonUserStep.users.add(it.users)
            }
            commonStepUser.add(commonUserStep)
        }
        println(gson.toJson(commonStepUser))
    }
}