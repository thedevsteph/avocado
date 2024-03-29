package com.example.login

class Utils {

    companion object {
        const val PRODUCT_ID = "PRODUCT ID"
        const val API_KEY = "effff9a325bc492580d73857b1e8a996"
        const val PRODUCT_INFO_BASE_URL = "https://api.spoonacular.com/food/products/"
        const val LOGIN_SUCCESSFUL_MESSAGE = "Login successful"
        const val INVALID_DETAILS_MESSAGE = "Sorry, there was an issue with your username or password"
        const val AUTH_COLLISION_ERROR_CODE = "ERROR_EMAIL_ALREADY_IN_USE"
        const val BACK_SLASH = "/"
        const val ERROR_TEXT = "There was error. Please try again, later."
        const val SEARCH_FOR_ITEM_TEXT = "Search the store"
        const val BASE_URL: String = "https://api.spoonacular.com/food/products/"


        private val priceArray: ArrayList<String> =
            arrayListOf("€9.99", "€20.99", "€12.49", "€6.45", "€18.49", "€11.99", "€10.49")
    }

    fun generateSinglePrice(): String {
        return priceArray.shuffled().first()
    }

    fun getFormattedProductUrl(productId: Int): String {
        return PRODUCT_INFO_BASE_URL + productId.toString() + BACK_SLASH
    }
}