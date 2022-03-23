package com.riac.marketapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riac.marketapp.util.DispatcherProvider
import com.riac.marketapp.util.Resource
import com.riac.marketapp.data.local.RoomRepository
import com.riac.marketapp.data.local.entities.ItemEnt
import com.riac.marketapp.data.local.entities.toItem
import com.riac.marketapp.domain.model.Item
import com.riac.marketapp.domain.model.ItemDetail
import com.riac.marketapp.domain.model.Query
import com.riac.marketapp.domain.model.toItemEnt
import com.riac.marketapp.domain.repository.MarketRepository
import com.riac.marketapp.util.Constants
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

    private var _searchQuery: MutableLiveData<Resource<Query>> = MutableLiveData()
    val searchQuery: LiveData<Resource<Query>> = _searchQuery
    private var _searchItem: MutableLiveData<Resource<ItemDetail>> = MutableLiveData()
    val searchItem: LiveData<Resource<ItemDetail>> = _searchItem
    private var _recentItems: MutableLiveData<Resource<List<Item>>> = MutableLiveData()
    val recentItems: LiveData<Resource<List<Item>>> = _recentItems

    //
    private var _selectedItem: MutableLiveData<Item> = MutableLiveData()
    val selectedItem: LiveData<Item> = _selectedItem

    //
    private var searchJob: Job? = null

    fun makeSearch(searchArg: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(dispatcher.default) {
            _searchQuery.postValue(Resource.Loading())
            val response = repository.getSearchResults(searchArg)
            _searchQuery.postValue(
                if (response.data == null) {
                    response.message?.let { Resource.Error(it) }
                } else {
                    Resource.Success(response.data)
                }
            )
        }
    }

    fun searchItem(id: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(dispatcher.default) {
            _searchItem.postValue(Resource.Loading())
            val response = repository.getItemByID(id)
            _searchItem.postValue(
                if (response.data == null) {
                    response.message?.let { Resource.Error(it) }
                } else {
                    Resource.Success(response.data)
                }
            )
        }
    }

    fun setSelectedItem(item: Item) {
        _selectedItem.value = item
    }

    fun saveLastViewedItem(item: Item) {
        roomRepository.insertRecord(item.toItemEnt())
    }

    fun getRecentlyViewedItems() {
        viewModelScope.launch(dispatcher.default) {
            _recentItems.postValue(Resource.Loading())
            val data = roomRepository.getLastFiveRecords()
            _recentItems.postValue(handleGetRecentViewedItems(data))
        }

    }

    private fun handleGetRecentViewedItems(data: List<ItemEnt>): Resource<List<Item>> =
        if (data.isNullOrEmpty()) {
            Resource.Error(Constants.LOCAL_DB_ERROR)
        } else {
            val recentViewedItems = arrayListOf<Item>()
            data.forEach {
                recentViewedItems.add(it.toItem())
            }
            Resource.Success(recentViewedItems)
        }


}