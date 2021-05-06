package com.rapps.testrollingglory.ui.listGift

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

class GiftListViewModel @ViewModelInject constructor(private val repository: GiftListRepository) :
    ViewModel() {

    val listGift = repository.getListResult().cachedIn(viewModelScope)

}