package com.doordash.demo.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doordash.demo.model.Restaurant
import com.doordash.demo.retrofit.RestaurantAPI
import com.doordash.demo.retrofit.RetrofitClient
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class HomeViewModel : ViewModel() {

    private var restaurantAPI: RestaurantAPI
    private var mutableLiveData:MutableLiveData<RestaurantLiveData> = MutableLiveData()

    var subscription: Subscription? = null

    init {
        var retrofitClient = RetrofitClient.getInstance()
        restaurantAPI = retrofitClient.create(RestaurantAPI::class.java)
    }

    /**
     *  @param lat
     *  @param lng
     *  @param offset
     *  @param limit
     */
    fun fetchData(lat:String, lng:String, offset:Int, limit:Int) {

           subscription = restaurantAPI.getRestaurantList(lat, lng, offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->  buildRestaurantLiveData(result)},
                    { error -> Log.e("HomeViewModel", "Error fetching data. ${error.cause?.message}")
                        buildRestaurantLiveData(ArrayList())})

    }

    fun removeSubscription() {
        val isUnsubscribed = subscription?.isUnsubscribed ?: false
        if (!isUnsubscribed) {
            subscription?.unsubscribe()
        }
    }

    /**
     * @param it Build restaurant list
     */
    fun buildRestaurantLiveData(it: List<Restaurant>) {
        if (!it.isNullOrEmpty()) {
            mutableLiveData.postValue(RestaurantLiveData(it, false))
        } else {
            mutableLiveData.postValue(RestaurantLiveData(it, true))
        }
    }

    fun getRestaurantList():LiveData<RestaurantLiveData> {
        return mutableLiveData
    }

    class RestaurantLiveData(val restaurantLiveList:List<Restaurant>, val isError:Boolean)
}