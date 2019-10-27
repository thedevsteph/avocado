package com.example.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.login.productInformation.NutritionData

class ProductNutritionAdapter(private val nutritionData: NutritionData) :
    RecyclerView.Adapter<ProductNutritionAdapter.ProductNutritionViewHolder>() {

    companion object {
        const val NUTRITION_INFORMATION_SIZE = 4
        const val CALORIES = "Calories"
        const val FAT = "Fat"
        const val PROTEIN = "Protein"
        const val CARBS = "Carbs"
        var datastore = ArrayList<Pair<String, String>>(NUTRITION_INFORMATION_SIZE)
    }

    class ProductNutritionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nutritionValueValue: TextView = view.findViewById(R.id.nutrition_value_value)
        val nutritionValueText: TextView = view.findViewById(R.id.nutrition_value_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductNutritionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_content_grid, parent, false) as View

        return ProductNutritionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductNutritionViewHolder, position: Int) {
        setUpDataHolder()
        datastore[position].apply {
            holder.nutritionValueText.text = first
            holder.nutritionValueValue.text = second
        }
    }

    private fun setUpDataHolder() {
        datastore.add(Pair(CALORIES, nutritionData.calories.toString()))
//        datastore.add(Pair(FAT, nutritionData.fat!!))
//        datastore.add(Pair(CARBS, nutritionData.carbohydrates!!))
//        datastore.add(Pair(PROTEIN, nutritionData.protein!!))
    }

    override fun getItemCount(): Int {
        return NUTRITION_INFORMATION_SIZE
    }

}