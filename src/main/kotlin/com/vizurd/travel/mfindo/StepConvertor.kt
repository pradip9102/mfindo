package com.vizurd.travel.mfindo

import com.vizurd.travel.mfindo.models.Example
import com.vizurd.travel.mfindo.utils.Constants

class StepConvertor {

    fun fetchFromApi() {
        val networkRepo = RetrofitProvider(Constants.BASE_URL).getNetworkRepository()
        networkRepo.listRepos("kurla", "vakola", "driving")
                .flatMap { respon ->
                    println("Response 1 => ${respon.routes}")
                    return@flatMap networkRepo.listRepos("kurla", "andheri", "driving")
                }
                .subscribe({ result ->
                    println("Response 2 => ${result.routes}")
                }, { err -> err.printStackTrace() })

    }

    private fun findCommonPath(user1: List<Example>, user2: List<Example>) {
        println(user1)
        println(user2)
    }
}