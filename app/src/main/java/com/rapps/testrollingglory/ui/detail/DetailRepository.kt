package com.rapps.testrollingglory.ui.detail

import com.rapps.testrollingglory.api.ApiInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailRepository @Inject constructor(private val apiInterface: ApiInterface){

    suspend fun getDetail(id:String) = apiInterface.getDetail(id)

    fun setWishlisht(id:String) = apiInterface.setWishlist(id)
}