package com.doordash.demo.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.doordash.demo.model.Restaurant
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    // Run tasks synchronously
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    var restaurants = ArrayList<Restaurant>()

    @Before
    fun initViewModel() {
        var restaurant = Restaurant("AB", "Desc 1", 40)
        restaurants.add(restaurant)
        restaurant = Restaurant("CD", "Desc 2", 50)
        restaurants.add(restaurant)
        viewModel = HomeViewModel()
    }

    @Test
    fun testLiveDataEmittingForNonEmptyRestaurantList() {

        viewModel.buildRestaurantLiveData(restaurants)

        assertTrue(viewModel.getRestaurantList().value?.restaurantLiveList?.size == 2)
        assertFalse(viewModel.getRestaurantList().value?.isError ?: false)
    }

    @Test
    fun testLiveDataEmittingForEmptyRestaurantList() {

        viewModel.buildRestaurantLiveData(emptyList())

        assertTrue(viewModel.getRestaurantList().value?.restaurantLiveList?.size == 0)
        assertTrue(viewModel.getRestaurantList().value?.isError ?: false)
    }
}