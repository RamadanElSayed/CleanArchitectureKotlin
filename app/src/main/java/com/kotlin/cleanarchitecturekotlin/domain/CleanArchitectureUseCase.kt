package com.kotlin.cleanarchitecturekotlin.domain

import android.text.TextUtils
import com.example.task.model.personimages.PersonImagesModel
import com.example.task.model.popularpeople.PopularPeopleModel
import com.kotlin.cleanarchitecturekotlin.data.repository.CleanArchitectureRepository
import io.reactivex.Observable
import retrofit2.Call

class CleanArchitectureUseCase (val repo: CleanArchitectureRepository){

    fun popularPeople_List(
        api_key: String,
        language: String,
        page: Int
    ): Observable<PopularPeopleModel> {
        return repo.popularPeople_List(api_key, language, page)

    }


    fun searchPeople(
        api_key: String,
        language: String,
        query: String,
        page: Int,
        include_adult: Boolean,
        region: String
    ): Observable<PopularPeopleModel> {
        return repo.searchPeople(api_key, language, query, page, include_adult, region)
    }


    fun personImages(
        person_id: String
        , api_key: String
    ): Call<PersonImagesModel> {
        return repo.personImages(person_id, api_key)
    }

    fun validateInputField(input: String?): Boolean {
        return !TextUtils.isEmpty(input)
    }
}