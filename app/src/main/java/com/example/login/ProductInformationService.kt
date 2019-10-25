package com.example.login

import io.reactivex.Observable
import retrofit2.http.GET

interface ProductInformationService{

    companion object{
        private const val API_KEY = Utils.API_KEY
    }

    @GET("?apiKey=$API_KEY")
    fun getProductData() : Observable<ProductInformationData>

}