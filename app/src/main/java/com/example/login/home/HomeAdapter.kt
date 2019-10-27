package com.example.login.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.login.FoodItemData
import com.example.login.productInformation.ProductInformationActivity
import com.example.login.R
import com.example.login.Utils
import com.squareup.picasso.Picasso

class HomeAdapter(private val foodItemList: FoodItemData) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    companion object {
        private val utils: Utils = Utils()
    }

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImageView = view.findViewById<ImageView?>(R.id.single_recipe_item_image)
        val description = view.findViewById<TextView?>(R.id.single_recipe_item_description)
        val price = view.findViewById<TextView?>(R.id.single_recipe_item_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_recipe_item_view, parent, false) as View

        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        var productId = foodItemList.result[position].id
        holder.apply {
            description?.text = foodItemList.result[position].name
            price?.text = utils.generateSinglePrice()
            itemImageView?.let { getImage(foodItemList.result[position].imageUrl, itemImageView) }
        }
        holder.itemView.setOnClickListener { startProductInfoActivity(holder, productId) }
    }

    private fun startProductInfoActivity(holder: HomeViewHolder, productId: Int) {
        val context: Context = holder.itemView.context
        var intent = Intent(context, ProductInformationActivity::class.java)
        intent.putExtra(Utils.PRODUCT_ID, productId)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return foodItemList.result.size
    }

    private fun getImage(imageUrl: String, imageView: ImageView) {
        Picasso.get()
            .load(imageUrl)
            .into(imageView)
    }
}