package com.example.login.productInformation

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductInformationData(
    @Expose
    @SerializedName("id")
    var id: Int,

    @Expose
    @SerializedName("title")
    var name: String?,

    @Expose
    @SerializedName("images")
    val imageUrls: Array<String>?,

    @Expose
    @SerializedName("badges")
    val badges: ArrayList<String?>,

    @Expose
    @SerializedName("important_badges")
    val importantBadges: ArrayList<String?>,

    @Expose
    @SerializedName("nutrition")
    val nutritionData: NutritionData,

    @Expose
    @SerializedName("serving_size")
    val servingSize: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductInformationData

        if (id != other.id) return false
        if (name != other.name) return false
        if (imageUrls != null) {
            if (other.imageUrls == null) return false
            if (!imageUrls.contentEquals(other.imageUrls)) return false
        } else if (other.imageUrls != null) return false
        if (badges != other.badges) return false
        if (importantBadges != other.importantBadges) return false
        if (nutritionData != other.nutritionData) return false
        if (servingSize != other.servingSize) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (imageUrls?.contentHashCode() ?: 0)
        result = 31 * result + badges.hashCode()
        result = 31 * result + importantBadges.hashCode()
        result = 31 * result + nutritionData.hashCode()
        result = 31 * result + (servingSize?.hashCode() ?: 0)
        return result
    }
}

data class NutritionData(

    @Expose
    @SerializedName("calories")
    val calories: Int?,

    @Expose
    @SerializedName("fat")
    val fat: String?,

    @Expose
    @SerializedName("protein")
    val protein: String?,

    @Expose
    @SerializedName("carbs")
    val carbohydrates: String?
)