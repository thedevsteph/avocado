package com.example.login

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ProductInformationActivity : AppCompatActivity() {

    companion object {
        var productId: Int = 0
        const val DEFAULT_PRODUCT_VALUE = 0
        var myCompositeDisposable: CompositeDisposable = CompositeDisposable()
        lateinit var recyclerView: RecyclerView
        var utils: Utils = Utils()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_information)
        productId = intent.getIntExtra(Utils.PRODUCT_ID, DEFAULT_PRODUCT_VALUE)

        recyclerView = findViewById(R.id.nutrition_value_recycler_view)
        recyclerView.apply {
            layoutManager =  LinearLayoutManager(this@ProductInformationActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        loadData()
    }

    private fun loadData() {
        val requestBuilder = Retrofit.Builder()
            .baseUrl(utils.getFormattedProductUrl(productId))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(ProductInformationService::class.java)

        myCompositeDisposable.add(
            requestBuilder.getProductData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::populateViews)
        )
    }

    private fun populateViews(productInformationData: ProductInformationData){
        val selectedProductImage: ImageView = findViewById(R.id.product_information_image)
        getImageFromUrl(productInformationData.imageUrls?.get(1),selectedProductImage)
        recyclerView.adapter = ProductNutritionAdapter(productInformationData.nutritionData)

    }

    private fun getImageFromUrl(imageUrl: String?,  imageView: ImageView) {
        Picasso.get()
            .load(imageUrl)
            .into(imageView)
    }


}
