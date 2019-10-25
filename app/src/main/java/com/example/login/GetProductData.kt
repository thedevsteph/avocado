package com.example.login

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GetProductData{

    companion object{
        private const val API_KEY = Utils.API_KEY
    }

    @GET("search?apiKey=$API_KEY")
    fun getRecipeSearchData(@Query("query") query: String) : Observable<FoodItemData>

}