package com.riac.marketapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riac.marketapp.common.DispatcherProvider
import com.riac.marketapp.common.Resource
import com.riac.marketapp.data.local.RoomRepository
import com.riac.marketapp.data.local.entities.ItemEnt
import com.riac.marketapp.data.local.entities.toItem
import com.riac.marketapp.domain.model.Item
import com.riac.marketapp.domain.model.ItemDetail
import com.riac.marketapp.domain.model.Query
import com.riac.marketapp.domain.model.toItemEnt
import com.riac.marketapp.domain.repository.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MarketRepository,
    private val dispatcher: DispatcherProvider,
    private val roomRepository: RoomRepository
) : ViewModel() {

    var searchQuery: MutableLiveData<Resource<Query>> = MutableLiveData()
    var searchItem: MutableLiveData<Resource<ItemDetail>> = MutableLiveData()
    var recentItems: MutableLiveData<Resource<List<Item>>> = MutableLiveData()

    //
    var selectedItem: MutableLiveData<Item> = MutableLiveData()

    //
    private var searchJob: Job? = null

    fun makeSearch(searchArg: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(dispatcher.default) {
            searchQuery.postValue(Resource.Loading())
            val response = repository.getSearchResults(searchArg)
            searchQuery.postValue(handleGetResults(response))
        }
    }

    fun searchItem(id: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(dispatcher.default) {
            searchItem.postValue(Resource.Loading())
            val response = repository.getItemByID(id)
            searchItem.postValue(handleGetItem(response))
        }
    }

    private fun handleGetItem(response: Resource<ItemDetail>): Resource<ItemDetail> =
        if (response.data == null) {
            Resource.Error(response.message!!)
        } else {
            Resource.Success(response.data)
        }


    private fun handleGetResults(resp: Resource<Query>): Resource<Query> =
        if (resp.data == null) {
            Resource.Error(resp.message!!)
        } else {
            Resource.Success(resp.data)
        }

    fun setSelectedItem(item: Item) {
        selectedItem.value = item
    }


    fun saveLastViewedItem(item: Item) {
        roomRepository.insertRecord(item.toItemEnt())
    }

    fun getRecentlyViewedItems() {
        viewModelScope.launch(dispatcher.default) {
            recentItems.postValue(Resource.Loading())
            val data = roomRepository.getLastFiveRecords()
            recentItems.postValue(handleGetRecentViewedItems(data))
        }

    }

    private fun handleGetRecentViewedItems(data: List<ItemEnt>): Resource<List<Item>> =
        if (data.isNullOrEmpty()) {
            Resource.Error("Couldnt retrieve data from local DB")
        } else {
            val recentViewedItems = arrayListOf<Item>()
            data.forEach {
                recentViewedItems.add(it.toItem())
            }
            Resource.Success(recentViewedItems)
        }


}