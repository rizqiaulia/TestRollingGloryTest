package com.rapps.testrollingglory.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    companion object {
        const val BASE_URL = "https://recruitment.dev.rollingglory.com/api/v2/"
    }

    @GET("gifts")
    suspend fun getListGift(
        @Query("page[number]") page: Int,
        @Query("page[size]") perPage: Int
    ): GiftResponse

    @GET("gifts/{gift-id}")
    suspend fun getDetail(
        @Path("gift-id") gift: String
    ): DetailResponse

    @POST("gifts/{gift-id}/wishlist")
    fun setWishlist(
        @Path("gift-id") giftId:String
    ): Call<ResponseBody>
}