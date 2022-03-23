package com.riac.marketapp.domain.repository

import com.riac.marketapp.util.Resource
import com.riac.marketapp.domain.model.ItemDetail
import com.riac.marketapp.domain.model.Query


interface MarketRepository {
    suspend fun getSearchResults(searchArg: String): Resource<Query>
    suspend fun getItemByID(id: String): Resource<ItemDetail>
}