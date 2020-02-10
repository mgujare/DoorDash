package com.doordash.demo.retrofit

import com.doordash.demo.model.Restaurant
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestaurantAPI {

    @GET("restaurant")
    fun getRestaurantList(@Query("lat")lat:String, @Query("lng")lng:String,
                          @Query("offset")offset:Int, @Query("limit")limit:Int): Observable<List<Restaurant>>

    @GET("restaurant/{id}")
    fun getRestaurantDetail(@Path("id")id:String): Observable<Restaurant>

}