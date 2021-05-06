package com.rapps.testrollingglory.ui.detail

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rapps.testrollingglory.api.DetailResponse
import com.rapps.testrollingglory.api.GiftResponse
import com.rapps.testrollingglory.data.ListGift
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel @ViewModelInject constructor(private val repository: DetailRepository):ViewModel() {

    private var _detail = MutableLiveData<ListGift>()
    val detail :LiveData<ListGift> get() = _detail

    private var _responseCode = MutableLiveData<Int>()
    val responseCode :LiveData<Int> get() = _responseCode

    fun getDetail(id:String){
        viewModelScope.launch {
            val detailGift = repository.getDetail(id)
            _detail.postValue(detailGift.data)
        }
    }

    fun setWishlist(id: String){
        val responseApi = repository.setWishlisht(id)

        responseApi.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    _responseCode.value = response.code()
                }else{
                    _responseCode.value = response.code()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                _responseCode.value = 9999
            }

        })
    }
}