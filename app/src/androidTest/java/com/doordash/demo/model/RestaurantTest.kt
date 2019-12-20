package com.doordash.demo.model

import android.os.Parcel
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RestaurantTest {

    private var restaurant = Restaurant("Carl's Jr.", "Fastfood", 50)

    @Test
    fun restaurantParcelableTest() {
        restaurant.coverImageUrl = "https://testimageurl.com/test.png"

        val parcel = Parcel.obtain()
        restaurant.writeToParcel(parcel, restaurant.describeContents())
        parcel.setDataPosition(0)

        val restaurantCreatedFromParcel =
            Restaurant.createFromParcel(parcel)

        Assert.assertTrue(restaurantCreatedFromParcel.name.equals(restaurant.name))
        Assert.assertTrue(restaurantCreatedFromParcel.description.equals(restaurant.description))
        Assert.assertTrue(restaurantCreatedFromParcel.asapTime == 50)
        Assert.assertTrue(restaurantCreatedFromParcel.coverImageUrl.equals(restaurant.coverImageUrl))
    }
}