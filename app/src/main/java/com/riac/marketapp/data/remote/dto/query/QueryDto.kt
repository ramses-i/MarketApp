package com.riac.marketapp.data.remote.dto.query

import com.riac.marketapp.domain.model.Item
import com.riac.marketapp.domain.model.Query

/**
 *  DISCLAIMER: I DID NOT CREATE ALL OF THESE CLASSES MANUALLY,
 *  I MADE A REQUEST TO: https://api.mercadolibre.com/sites/MLU/search?q=notebook
 *  COPIED THE RESPONSE INTO THE PLUGIN: KOTLIN DATA CLASS FROM JSON
 */

data class QueryDto(
    val available_filters: List<AvailableFilter>,
    val available_sorts: List<AvailableSort>,
    val country_default_time_zone: String,
    val filters: List<Filter>,
    val paging: Paging,
    val query: String,
    val results: List<Result>,
    val site_id: String,
    val sort: Sort
)


fun QueryDto.toQuery(): Query {
    val itemList = arrayListOf<Item>()
    results.forEach {
        itemList.add(it.toItem())
    }
    return Query(site_id, itemList)
}