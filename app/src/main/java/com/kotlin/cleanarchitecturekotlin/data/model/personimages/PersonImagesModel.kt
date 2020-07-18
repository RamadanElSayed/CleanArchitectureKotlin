package com.example.task.model.personimages


import com.google.gson.annotations.SerializedName
//import android.support.annotation.Keep
import androidx.annotation.Keep

@Keep
data class PersonImagesModel(
    @SerializedName("id") var id: Int,
    @SerializedName("profiles") var profiles: List<Profile>
)