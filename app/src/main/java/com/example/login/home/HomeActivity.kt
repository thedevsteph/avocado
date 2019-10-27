package com.example.login.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login.FoodItemData
import com.example.login.GridSpacingItemDecoration
import com.example.login.R
import com.example.login.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val NUMBER_OF_COLUMNS: Int = 2
        private var recyclerView: RecyclerView? = null
        private var searchedText: String? = null
        private lateinit var viewModel: HomeViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        ActivityHomeBinding.inflate(LayoutInflater.from(this)).apply {
            setContentView(root)
            viewModel = ViewModelProviders.of(this@HomeActivity).get(HomeViewModel::class.java)
            homeViewModel = viewModel
            setLifecycleOwner(this@HomeActivity)
            recyclerView = this.searchItemsRecyclerView
        }

        recyclerView?.apply {
            addItemDecoration(GridSpacingItemDecoration(2, 50, true))
            layoutManager = GridLayoutManager(this@HomeActivity, NUMBER_OF_COLUMNS)
            updateRecyclerView()
        }

        viewModel.showDefaultText()

        recipe_search_bar.setOnEditorActionListener { _, actionId, _ ->
            searchedText = recipe_search_bar.text.toString()
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                searchedText?.let { text ->
                    viewModel?.loadData(text)
                }
                hideSoftKeyboard()
            }
            true
        }
    }

    private fun updateRecyclerView(){
        viewModel.foodData.observe(this@HomeActivity, foodDataObserver)
    }

    private val foodDataObserver: Observer<FoodItemData> = Observer{ data ->
        recyclerView?.adapter = HomeAdapter(data)
    }

    private fun hideSoftKeyboard() {
        val view: View? = this.currentFocus
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onPause() {
        super.onPause()
        viewModel?.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.dispose()
    }
}
