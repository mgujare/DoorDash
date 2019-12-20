package com.doordash.demo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.doordash.demo.R
import com.doordash.demo.ui.adapter.RestaurantAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    val lat = "37.422740"
    val lng = "-122.139956"

    private var restaurantList:RecyclerView? = null
    private var refreshLayout:SwipeRefreshLayout? = null
    private var errorButton:Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        restaurantList = root.restaurant_list
        errorButton = root.error_button

        errorButton?.setOnClickListener {
            clearRestaurantData()
            fetchRestaurantData()
        }

        //SwipeToRefresh
        refreshLayout = root.findViewById(R.id.swipeContainer)
        refreshLayout?.setOnRefreshListener {
            clearRestaurantData()
            fetchRestaurantData()
        }

        //Adapter
        var restaurantAdapter = RestaurantAdapter(context, ArrayList())
        restaurantList?.layoutManager = LinearLayoutManager(context)
        restaurantList?.adapter = restaurantAdapter
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        restaurantList?.addItemDecoration(itemDecoration)


        //Fetch restaurant data.
        fetchRestaurantData()

        //Observe LiveData updates.
        homeViewModel.getRestaurantList()?.observe(this, Observer<HomeViewModel.RestaurantLiveData> {
            if (!it.isError) {
                (restaurantList?.adapter as RestaurantAdapter).updateList(ArrayList(it.restaurantLiveList))

            } else {
                errorButton?.isVisible = true
            }
            shouldShowProgressBar(false)
        })

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        homeViewModel.removeSubscription()
    }

    private fun fetchRestaurantData() {
        errorButton?.isVisible = false
        shouldShowProgressBar(true)
        homeViewModel.fetchData(lat, lng, 0, 50)
    }

    private fun clearRestaurantData() {
        (restaurantList?.adapter as RestaurantAdapter).clearList()
    }

    private fun shouldShowProgressBar(show:Boolean) {
     refreshLayout?.isRefreshing = show
    }
}