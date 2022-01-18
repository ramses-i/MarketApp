package com.riac.marketapp.data.remote

import com.riac.marketapp.data.remote.dto.item.ItemDto
import com.riac.marketapp.data.remote.dto.query.QueryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarketAPI {

    @GET("/sites/MLU/search")
    suspend fun makeSearchQuery(
        @Query("q") itemRequest: String
    ): Response<QueryDto>

    @GET("/items/{product_id}")
    suspend fun searchItemByID(
        @Path("product_id") item_id: String
    ): Response<ItemDto>
}