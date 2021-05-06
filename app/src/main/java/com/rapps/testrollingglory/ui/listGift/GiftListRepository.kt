package com.rapps.testrollingglory.ui.listGift

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.rapps.testrollingglory.api.ApiInterface
import com.rapps.testrollingglory.data.pagging.GiftPagingSource
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max

@Singleton
class GiftListRepository @Inject constructor(private val apiInterface: ApiInterface) {

    fun getListResult() =Pager(
        config = PagingConfig(pageSize = 4 , maxSize = 100,enablePlaceholders = false),
        pagingSourceFactory = {
            GiftPagingSource(apiInterface)
        }
    ).liveData
}