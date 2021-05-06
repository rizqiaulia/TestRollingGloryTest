package com.rapps.testrollingglory.data.pagging

import androidx.paging.PagingSource
import com.rapps.testrollingglory.api.ApiInterface
import com.rapps.testrollingglory.data.ListGift
import retrofit2.HttpException
import java.io.IOException


private const val STARTING_PAGE_INDEX = 1

class GiftPagingSource(private val apiInterface: ApiInterface) : PagingSource<Int, ListGift>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListGift> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = apiInterface.getListGift(position, params.loadSize)
            val datas = response.data

            LoadResult.Page(
                data = datas,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (datas.isEmpty()) null else position + 1
            )
        } catch (execption: IOException) {
            LoadResult.Error(execption)
        } catch (execption: HttpException) {
            LoadResult.Error(execption)
        }
    }
}