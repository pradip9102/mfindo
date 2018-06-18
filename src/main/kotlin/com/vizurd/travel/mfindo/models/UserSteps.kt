package com.vizurd.travel.mfindo.models

import com.google.maps.model.DirectionsStep

data class UserStep(var step: DirectionsStep?, var users: String)
data class CommonStepUser(var step: DirectionsStep?, var users: ArrayList<String>)