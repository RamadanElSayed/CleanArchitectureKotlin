package com.example.task.network
import com.example.task.model.personimages.PersonImagesModel
import com.example.task.model.popularpeople.PopularPeopleModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {
    @GET("person/popular")
    fun popularPeople_List(@Query("api_key") api_key: String,
                           @Query("language") language: String,
                           @Query("page") page: Int): Observable<PopularPeopleModel>

    @GET("search/person")
    fun searchPeople(@Query("api_key") api_key: String,
                     @Query("language") language: String,
                     @Query("query") query: String,
                     @Query("page") page: Int,
                     @Query("include_adult") include_adult: Boolean,
                     @Query("region") region: String): Observable<PopularPeopleModel>


    @GET("person/{person_id}/images")
    fun personImages(@Path(value = "person_id", encoded = true) person_id:String
                     ,@Query("api_key") api_key: String): Call<PersonImagesModel>
}