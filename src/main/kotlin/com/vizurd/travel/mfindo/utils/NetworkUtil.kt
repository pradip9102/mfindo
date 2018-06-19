package com.vizurd.travel.mfindo.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkUtil {
    companion object {

        fun provideOkHttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                    .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build()
        }

        private fun provideRetrofit(mBaseUrl: String = Constants.BASE_URL): Retrofit {
            return Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(GsonUtil.gsonBuilder.create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(mBaseUrl)
                    .client(NetworkUtil.provideOkHttpClient())
                    .build()
        }

    }
}