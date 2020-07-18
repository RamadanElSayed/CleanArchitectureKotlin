package com.example.task.model.popularpeople

import com.google.gson.annotations.SerializedName
//import android.support.annotation.Keep
import androidx.annotation.Keep


@Keep
data class PopularPeopleModel(
    @SerializedName("page") var page: Int,
    @SerializedName("results") var results: List<result>,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("total_results") var totalResults: Int
)