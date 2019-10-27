package com.example.login.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.login.FoodItemData
import com.example.login.GetProductData
import com.example.login.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel: ViewModel(){

    companion object{
        private val myCompositeDisposable: CompositeDisposable = CompositeDisposable()
    }

    private var _foodData: MutableLiveData<FoodItemData> = MutableLiveData()
    var foodData: LiveData<FoodItemData> = _foodData

    private var _isProgressBarLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var isProgressBarLoading: LiveData<Boolean> = _isProgressBarLoading

    private var _messageText: MutableLiveData<String> = MutableLiveData(" ")
    var messageText: LiveData<String> = _messageText

    private var _showMessageText: MutableLiveData<Boolean> = MutableLiveData(false)
    var showMessageText : LiveData<Boolean> = _showMessageText


     fun loadData(query: String) {
         showProgressBarLoading()
        val requestBuilder = Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GetProductData::class.java)

        myCompositeDisposable.add(
            requestBuilder.getRecipeSearchData(query)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError{ displayMessageText() }
                .doOnComplete { hideProgressBarLoading() }
                .subscribeOn(Schedulers.io())
                .subscribe(this::updateFoodData)
        )
    }

    private fun updateFoodData(foodItemData: FoodItemData){
        _foodData.value = foodItemData
    }

    fun clear(){
        myCompositeDisposable.clear()
    }

    fun dispose(){
        myCompositeDisposable.dispose()
    }

    private fun hideProgressBarLoading(){
        _isProgressBarLoading.value = false
    }

    private fun showProgressBarLoading(){
        _isProgressBarLoading.value = true
        _showMessageText.value = false

    }

    private fun displayMessageText(){
        _messageText.value = Utils.ERROR_TEXT
        _showMessageText.value = true
    }

     fun showDefaultText(){
        _messageText.value = Utils.SEARCH_FOR_ITEM_TEXT
        _showMessageText.value = true
    }




}