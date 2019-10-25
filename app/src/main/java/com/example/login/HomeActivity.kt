package com.example.login

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val BASE_URL: String = "https://api.spoonacular.com/food/products/"
        private const val NUMBER_OF_COLUMNS: Int = 2
        private var myCompositeDisposable: CompositeDisposable = CompositeDisposable()
        private var recyclerView: RecyclerView? = null
        private var searchedText: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        myCompositeDisposable = CompositeDisposable()

        recyclerView = findViewById(R.id.search_items_recycler_view)
        recyclerView?.apply {
            addItemDecoration(GridSpacingItemDecoration(2, 50, true))
            layoutManager = GridLayoutManager(this@HomeActivity, NUMBER_OF_COLUMNS)
        }

        val searchBar: EditText = findViewById(R.id.recipe_search_bar)
        searchBar.setOnEditorActionListener { _, actionId, _ ->
            searchedText = searchBar.text.toString()
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                searchedText?.let { text -> loadData(text) }
                hideSoftKeyboard()
            }
            true
        }
    }

    private fun loadData(query: String) {
        val requestBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GetProductData::class.java)

        myCompositeDisposable.add(
            requestBuilder.getRecipeSearchData(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        )
    }

    private fun handleResponse(foodItemListResponse: FoodItemData) {
        recyclerView?.adapter = HomeAdapter(foodItemListResponse)
    }

    private fun hideSoftKeyboard() {
        val view = this.currentFocus
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onPause() {
        super.onPause()
        myCompositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        myCompositeDisposable.dispose()
    }
}
