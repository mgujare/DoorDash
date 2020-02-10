package com.doordash.demo.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doordash.demo.model.Restaurant
import com.doordash.demo.repository.DoordashRepository
import com.doordash.demo.retrofit.RestaurantAPI
import com.doordash.demo.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var restaurantAPI: RestaurantAPI
    private var mutableLiveData:MutableLiveData<RestaurantLiveData> = MutableLiveData()
    private var doordashRepo:DoordashRepository
    var restaurantListFromDB:List<Restaurant>? = null

    //var subscription: Subscription? = null

    init {
        var retrofitClient = RetrofitClient.getInstance()
        restaurantAPI = retrofitClient.create(RestaurantAPI::class.java)
        doordashRepo = DoordashRepository(application)
    }

    /**
     *  @param lat
     *  @param lng
     *  @param offset
     *  @param limit
     */
    fun fetchData(lat:String, lng:String, offset:Int, limit:Int) {

        //From DB
        doordashRepo?.fetchRestaurantListFromDB()

       //From API.
       restaurantAPI.getRestaurantList(lat, lng, offset, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->  buildRestaurantLiveData(result)
                //Delete db and re-populate.
                doordashRepo?.deleteAndRepopulateAllRestaurants(result)},
                { error -> Log.e("HomeViewModel", "Error fetching data. ${error.cause?.message}")
                    restaurantListFromDB = doordashRepo?.getAllRestaurants()
                    Log.d("HomeViewModel", "Attempt to populate form DB ${restaurantListFromDB?.size}")
                    buildRestaurantLiveData(restaurantListFromDB)})

    }

    fun removeSubscription() {
//        val isUnsubscribed = subscription?.isUnsubscribed ?: false
//        if (!isUnsubscribed) {
//            subscription?.unsubscribe()
//        }
    }

    /**
     * @param it Build restaurant list
     */
    fun buildRestaurantLiveData(it: List<Restaurant>?) {

        if (!it.isNullOrEmpty()) {
            mutableLiveData.postValue(RestaurantLiveData(it, false))
        } else {
            mutableLiveData.postValue(RestaurantLiveData(it, true))
        }
    }

    fun getRestaurantList():LiveData<RestaurantLiveData> {
        return mutableLiveData
    }

    class RestaurantLiveData(val restaurantLiveList:List<Restaurant>?, val isError:Boolean)
}