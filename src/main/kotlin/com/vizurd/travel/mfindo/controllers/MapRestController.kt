package com.vizurd.travel.mfindo.controllers

import com.google.maps.DirectionsApi
import com.google.maps.model.DirectionsStep
import com.vizurd.travel.mfindo.models.CommonStepsForUsers
import com.vizurd.travel.mfindo.models.UserDirectionResult
import com.vizurd.travel.mfindo.models.UserRawStepModel
import com.vizurd.travel.mfindo.models.UserStep
import com.vizurd.travel.mfindo.services.GoogleMapsService
import com.vizurd.travel.mfindo.utils.GsonUtil
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.File

@RestController
class MapRestController(
        val googleMapsService: GoogleMapsService
) {
    val logger = KotlinLogging.logger { }

    @GetMapping("/maps/api/result")
    fun getResult(@RequestParam("origin1") origin1: String, @RequestParam("target1") target1: String, @RequestParam("origin2") origin2: String, @RequestParam("target2") target2: String): ResponseEntity<ArrayList<CommonStepsForUsers>> {
        val resultList = ArrayList<UserDirectionResult>()
        resultList.add(UserDirectionResult("A", DirectionsApi.getDirections(googleMapsService.getContext(), origin1, target1).await()))
        resultList.add(UserDirectionResult("B", DirectionsApi.getDirections(googleMapsService.getContext(), origin2, target2).await()))
        val commonPath = findCommonPath(getUserStepRawFromDirectionResult(resultList))
        return ResponseEntity(commonPath, HttpStatus.OK)
    }

    private fun getUserStepRawFromDirectionResult(userResults: ArrayList<UserDirectionResult>): UserRawStepModel {
        val users = ArrayList<String>()
        val directionsStep = ArrayList<Array<DirectionsStep>>()
        userResults.forEach { result ->
            users.add(result.user)
            directionsStep.add(result.result.routes[0].legs[0].steps)
        }
        val rawSteps = UserRawStepModel(users, directionsStep)
        return rawSteps
    }

    private fun findCommonPath(userRaw: UserRawStepModel): ArrayList<CommonStepsForUsers> {
        val user1 = userRaw.users[0]
        val steps1 = userRaw.steps[0]
        val user2 = userRaw.users[1]
        val steps2 = userRaw.steps[1]
        val allUserSteps = steps1.map { UserStep(it, user1) } + steps2.map { UserStep(it, user2) }
        val allSteps = (steps1 + steps2).distinctBy { Pair(it.startLocation, it.endLocation) }

        var commonStepUser = ArrayList<CommonStepsForUsers>()

        allSteps.forEach { step ->
            val commonStepsForUsers = CommonStepsForUsers(step, arrayListOf())
            allUserSteps
                    .filter { it.step!!.startLocation!! == step.startLocation }
                    .filter { it.step!!.endLocation!! == step.endLocation }
                    .forEach { commonStepsForUsers.users.add(it.users) }
            commonStepUser.add(commonStepsForUsers)
        }
        commonStepUser = commonStepUser.filter { stUser -> stUser.users.size > 1 } as ArrayList
        logger.debug { "commonStepUser size -> ${commonStepUser.size}" }
        println(GsonUtil.prettyJson(commonStepUser))
        printLatLong(steps1, fileName = "step1.txt")
        printLatLong(steps2, fileName = "step2.txt")
        return commonStepUser
    }

    private fun printLatLong(steps: Array<DirectionsStep>, fileName: String) {
        File(fileName).printWriter().use { out ->
            steps.forEach {
                out.println("start location: [${it.startLocation.lat}, ${it.startLocation.lng}]," +
                        "end location: [${it.endLocation.lat}, ${it.endLocation.lng}]")
            }
        }
    }

}