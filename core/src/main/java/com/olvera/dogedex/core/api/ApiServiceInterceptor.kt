package com.olvera.dogedex.core.api

import okhttp3.Interceptor
import okhttp3.Response

object ApiServiceInterceptor : Interceptor {

    const val NEED_AUTH_HEADER_KEY = "needs_authentication"

    private var sessionToken: String? = null

    fun setSessionToken(sessionToken: String) {
        ApiServiceInterceptor.sessionToken = sessionToken
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        if (request.header(NEED_AUTH_HEADER_KEY) != null) {
            // needs credentials
            if (sessionToken == null) {
                throw java.lang.RuntimeException("Need to be authenticated")
            } else {
                requestBuilder.addHeader("AUTH-TOKEN", sessionToken!!)
            }
        }

        return chain.proceed(requestBuilder.build())
    }
}