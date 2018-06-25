package com.vizurd.travel.mfindo.controllers

import com.google.maps.DirectionsApi
import com.google.maps.model.DirectionsStep
import com.vizurd.travel.mfindo.models.CommonStepsForUsers
import com.vizurd.travel.mfindo.models.UserDirectionResult
import com.vizurd.travel.mfindo.models.UserRawStepModel
import com.vizurd.travel.mfindo.services.GoogleMapsService
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

    private fun findCommonPath(userRaw: UserRawStepModel): ArrayList<CommonStepsForUsers>
    {
        var temp = ArrayList<CommonStepsForUsers>()

        val user1  = userRaw.users[0]
        val user2  = userRaw.users[1]
        val steps1 = userRaw.steps[0]
        val steps2  = userRaw.steps[1]

        if(user1 != user2)  /* For Same USer it wont calculate*/
        {
            logger.debug { "Users going to looking at common path" }
            logger.debug { "Length User 1 "+steps1.size+" length User 2 "+steps2.size }
            //printLatLong(steps1,"abc1.txt")
            //printLatLong(steps2,"abc2.txt")
            temp = LCPath(steps1,steps2,steps1.size,steps2.size)
        }
        else
        {
            logger.warn { "Same User Finding Common Path : "+user1+" "+user2 }
        }
        return temp
    }

    private fun matchPath(userSteps1 : Array<DirectionsStep>,userSteps2 :Array<DirectionsStep>, m:Int , n:Int): Boolean
    {
        if(userSteps1[m].startLocation.lat != userSteps2[n].startLocation.lat) return false
        if(userSteps1[m].startLocation.lng != userSteps2[n].startLocation.lng) return false
        if(userSteps1[m].endLocation.lat != userSteps2[n].endLocation.lat) return false
        if(userSteps1[m].endLocation.lng != userSteps2[n].endLocation.lng) return false
        if(userSteps1[m].distance.inMeters != userSteps2[n].distance.inMeters) return false

        return true
    }

    private fun LCPath(userSteps1 : Array<DirectionsStep>,userSteps2 :Array<DirectionsStep>, m:Int , n:Int): ArrayList<CommonStepsForUsers> {
        logger.debug { "Entering Longest common Path" }
        val LCLen = Array(m+1, {IntArray(n+1)})
        var result =0
        var row = 0
        var col = 0
        for(i in 0..m)
        {
            for(j in 0..n)
            {
                if (i == 0 || j == 0)
                    LCLen[i][j] = 0
                else if (matchPath(userSteps1,userSteps2,i-1,j-1))
                {
                    LCLen[i][j] = LCLen[i - 1][j - 1] + 1
                    if(result < LCLen[i][j]) {
                        result = LCLen[i][j]
                        row = i
                        col = j
                    }
                }
                else
                {
                    LCLen[i][j] = 0
                }
            }
        }
        if(result == 0)
        {
            logger.debug { "no Common Path" }
        }
        else
        {
            logger.debug { "Found Common Path in numbers of steps: " +result}
        }

        val commonStepUser = ArrayList<CommonStepsForUsers>()

        while (LCLen[row][col] != 0) {
            commonStepUser.add(CommonStepsForUsers(userSteps1[row-1], arrayListOf()))
            row--
            col-- /* Could be removed*/
        }
        commonStepUser.forEach{ it->
            logger.debug {it.step!!.startLocation.lat.toString() + "  "+ it.step!!.startLocation.lng +" "+ it.step!!.endLocation.lat +"  "+ it.step!!.endLocation.lng}
        }
        logger.debug { "leaving Longest common Path" }
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