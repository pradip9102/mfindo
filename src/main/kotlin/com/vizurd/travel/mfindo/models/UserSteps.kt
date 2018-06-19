package com.vizurd.travel.mfindo.models

import com.google.maps.model.DirectionsResult
import com.google.maps.model.DirectionsStep

data class UserStep(var step: DirectionsStep?, var users: String)
data class CommonStepsForUsers(var step: DirectionsStep?, var users: ArrayList<String>)
data class UserDirectionResult(var user: String,var result: DirectionsResult)
data class UserRawStepModel(var users: ArrayList<String>,var steps: ArrayList<Array<DirectionsStep>>)