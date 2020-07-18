package com.kotlin.cleanarchitecturekotlin.data.repository

import com.example.task.model.personimages.PersonImagesModel
import com.example.task.model.popularpeople.PopularPeopleModel
import com.example.task.network.APIInterface
import io.reactivex.Observable
import retrofit2.Call

class CleanArchitectureRepository(val apiInterface: APIInterface) {

    fun popularPeople_List(
        api_key: String,
        language: String,
        page: Int
    ): Observable<PopularPeopleModel> {
        return apiInterface.popularPeople_List(api_key, language, page)

    }


    fun searchPeople(
        api_key: String,
        language: String,
        query: String,
        page: Int,
        include_adult: Boolean,
        region: String
    ): Observable<PopularPeopleModel> {
        return apiInterface.searchPeople(api_key, language, query, page, include_adult, region)
    }


    fun personImages(
        person_id: String
        , api_key: String
    ): Call<PersonImagesModel> {
        return apiInterface.personImages(person_id, api_key)
    }

}