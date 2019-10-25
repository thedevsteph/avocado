package com.example.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FoodItemData(

    @Expose
    @SerializedName("products")
    var result: List<ProductData>
)


data class ProductData(

    @Expose
    @SerializedName("id")
    var id: Int,

    @Expose
    @SerializedName("title")
    var name: String,

    @Expose
    @SerializedName("image")
    val imageUrl: String

)