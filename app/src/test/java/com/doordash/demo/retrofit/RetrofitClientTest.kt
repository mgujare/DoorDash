package com.doordash.demo.retrofit

import org.junit.Assert
import org.junit.Test
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory

class RetrofitClientTest {

    private val retrofitClient = RetrofitClient.getInstance()

    @Test
    fun testRetrofitInstanceUrl() {
        Assert.assertEquals(retrofitClient.baseUrl().url().toString(), RetrofitClient.BASE_RESTAURANT_URL)
        Assert.assertTrue(retrofitClient.baseUrl().isHttps)
    }

    @Test
    fun testRetrofitInstanceFactories() {
        Assert.assertFalse(retrofitClient.callAdapterFactories().isEmpty())
        Assert.assertFalse(retrofitClient.converterFactories().isEmpty())
        Assert.assertTrue(retrofitClient.callAdapterFactories()[0] is RxJavaCallAdapterFactory)
    }

}