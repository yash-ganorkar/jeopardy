package com.yashganorkar.jeopardy.services

import okhttp3.Interceptor
import okhttp3.Response

class JeopardyRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url().newBuilder().build()

        val newRequest = request.newBuilder().url(url).build()

        return chain.proceed(newRequest)
    }

}