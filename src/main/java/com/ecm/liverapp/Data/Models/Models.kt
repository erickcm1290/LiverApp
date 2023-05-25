package com.ecm.liverapp.Data.Models

import com.google.gson.annotations.SerializedName

class Models {
    data class Respuesta(
        @SerializedName("status") var status: Status,
        @SerializedName("pageType") var pageType: String,
        @SerializedName("plpResults") var plpResults: PlpResults,
        @SerializedName("limit") var limit: Int
    )

    data class Status (
        @SerializedName("status") var status: String,
        @SerializedName("statusCode") var statusCode: Long
    )

    data class PlpResults(
        @SerializedName("label") var label: String,
        @SerializedName("records") var records: List<Record>,
    )

    data class Record(
        @SerializedName("productID") var productID: String,
        @SerializedName("productDisplayName") var productDisplayName: String,
        @SerializedName("smImage") var smImage: String,
        @SerializedName("listPrice") var listPrice: Double,
        @SerializedName("promoPrice") var promoPrice: Double,
        @SerializedName("brand") var brand: String,
        @SerializedName("variantsColor") var variantsColor: List<VariantsColor>
    )

    data class VariantsColor(
        @SerializedName("colorName") var colorName: String,
        @SerializedName("colorHex") var colorHex: String,
        @SerializedName("colorImageURL") var colorImageURL: String
    )

}