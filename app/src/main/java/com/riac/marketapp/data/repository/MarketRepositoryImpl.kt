package com.riac.marketapp.data.repository

import com.riac.marketapp.util.Resource
import com.riac.marketapp.data.local.AppDao
import com.riac.marketapp.data.remote.MarketAPI
import com.riac.marketapp.data.remote.dto.item.toItemDetail
import com.riac.marketapp.data.remote.dto.query.toQuery
import com.riac.marketapp.domain.model.ItemDetail
import com.riac.marketapp.domain.model.Query
import com.riac.marketapp.domain.repository.MarketRepository
import java.lang.Exception
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val api: MarketAPI,
    private val dao: AppDao
) :
    MarketRepository {

    override suspend fun getSearchResults(searchArg: String): Resource<Query> {
        return try {
            val response = api.makeSearchQuery(searchArg)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result.toQuery())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred during the query: getSearchResults.")
        }
    }

    override suspend fun getItemByID(id: String): Resource<ItemDetail> {
        return try {
            val response = api.searchItemByID(id)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result.toItemDetail())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred during the query: getItemByID.")
        }
    }

}