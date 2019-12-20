package com.doordash.demo.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.doordash.demo.R
import com.doordash.demo.model.Restaurant
import kotlinx.android.synthetic.main.restaurant_list_item.view.*

class RestaurantAdapter(private val context: Context?, private var restaurantList: ArrayList<Restaurant>) : RecyclerView.Adapter<RestaurantView>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantView {
        val view:View = LayoutInflater.from(context).inflate(R.layout.restaurant_list_item, parent, false)
        return RestaurantView(view)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: RestaurantView, position: Int) {
        var restaurantItem = restaurantList?.get(position)
        holder.name.text = restaurantItem?.name
        holder.description.text = restaurantItem?.description
        if (restaurantItem?.asapTime == 0) {
            holder.timeToDelivery.text = context?.getString(R.string.restaurant_status_closed)
        } else {
            holder.timeToDelivery.text = restaurantItem?.asapTime.toString() + " " + context?.getString(R.string.delivery_asap_time_unit)
        }
        context?.let { Glide.with(context).
            load(restaurantItem?.coverImageUrl)
            .transform(RoundedCorners(10))
            .into(holder.coverImage)}
    }

    fun updateList(restaurantListParam: ArrayList<Restaurant>) {
        restaurantList.clear()
        restaurantList.addAll(restaurantListParam)
        notifyDataSetChanged()
    }

    fun clearList() {
        restaurantList.clear()
        notifyDataSetChanged()
    }

}

class RestaurantView(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var name: TextView = itemView.restaurant_name
    var description: TextView = itemView.restaurant_desc
    var timeToDelivery:TextView = itemView.time_to_delivery
    var coverImage: ImageView = itemView.cover_image

}