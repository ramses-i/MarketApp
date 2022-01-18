package com.riac.marketapp.data.remote.dto.query

data class Filter(
    val id: String,
    val name: String,
    val type: String,
    val values: List<ValueX>
)