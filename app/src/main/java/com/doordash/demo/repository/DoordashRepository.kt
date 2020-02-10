package com.doordash.demo.repository

import android.app.Application
import android.util.Log
import com.doordash.demo.model.Restaurant
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class DoordashRepository {

    private var doordashDao:RestaurantDao? = null
    private var restaurantList:List<Restaurant>? = null

    constructor(application: Application) {
        val doordashDatabase = DoordashDatabase.getDatabaseInstance(application)
        doordashDao = doordashDatabase?.doordashDao()
    }

    fun insertRestaurants(list:List<Restaurant>) {
        Completable.fromAction(Action {
            doordashDao?.addRestaurants(list)
        }).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("HomeViewModel", "DB insertion success. ${list.size}")
                fetchRestaurantListFromDB()
            },
                { error -> Log.d("HomeViewModel", "Error inserting DB " + error.message)})


    }

    fun deleteRestaurant(restaurant: Restaurant) {
        Completable.fromAction(Action {
            doordashDao?.deleteRestaurant(restaurant)
        }).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("HomeViewModel", "Restaurant deletion success.")
            },
                { error -> Log.d("HomeViewModel", "Error deleting Restaurant from DB " + error.message)})
    }

    fun deleteAllRestaurants() {
        Completable.fromAction(Action {
            doordashDao?.deleteRestaurants()
        }).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("HomeViewModel", "DB deletion success.")
            },
                { error -> Log.d("HomeViewModel", "Error deleting DB " + error.message)})

    }

    fun deleteAndRepopulateAllRestaurants(list:List<Restaurant>) {
        Completable.fromAction(Action {
            doordashDao?.deleteRestaurants()
        }).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("HomeViewModel", "DB deletion success.")
                insertRestaurants(list)
            },
                { error -> Log.d("HomeViewModel", "Error deleting All restaurants from DB " + error.message)})
    }

    fun fetchRestaurantListFromDB() {
        doordashDao?.getAllRestaurants()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                restaurantList = it
                Log.d("HomeViewModel", "DB population success. " + it.size)
            },
                { error -> Log.d("HomeViewModel", "Error getting restaurants from DB " + error.message)})

    }

    fun getAllRestaurants() : List<Restaurant>? {
        return restaurantList
    }
}