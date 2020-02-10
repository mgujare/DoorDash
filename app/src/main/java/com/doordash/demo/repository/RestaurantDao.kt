package com.doordash.demo.repository


import androidx.room.*
import com.doordash.demo.model.Restaurant
import io.reactivex.Flowable

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRestaurant(restaurant:Restaurant)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRestaurants(restaurants:List<Restaurant>)

    @Delete
    fun deleteRestaurant(restaurant: Restaurant)

    @Update
    fun updateRestaurant(restaurant: Restaurant)

    @Query("SELECT * FROM restaurants")
    fun getAllRestaurants(): Flowable<List<Restaurant>>

    @Query("DELETE FROM restaurants")
    fun deleteRestaurants()

}