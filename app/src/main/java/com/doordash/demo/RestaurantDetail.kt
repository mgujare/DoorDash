package com.doordash.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doordash.demo.ui.restaurantdetail.RestaurantDetailFragment

class RestaurantDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurant_detail_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RestaurantDetailFragment.newInstance())
                .commitNow()
        }
    }

}
