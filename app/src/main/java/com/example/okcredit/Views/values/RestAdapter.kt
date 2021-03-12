package com.example.okcredit.Views.values

import com.example.okcredit.BuildConfig

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


object RestAdapter {

    fun getInstance(): RestAPI? {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://kasbon.xyz/api/")
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(RestAPI::class.java)
    }

    private fun getClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) BODY else NONE
        val client: OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            // .addNetworkInterceptor(AddHeaderInterceptor())
            .connectTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .build()
        client.connectionPool.evictAll()
        return client
    }

    class AddHeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder: Request.Builder = chain.request().newBuilder()
            builder.addHeader("Content-Type", "application/json")
            return chain.proceed(builder.build())
        }
    }
}