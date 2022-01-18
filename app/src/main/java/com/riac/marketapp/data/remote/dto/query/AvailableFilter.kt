package com.riac.marketapp.data.remote.dto.query

data class AvailableFilter(
    val id: String,
    val name: String,
    val type: String,
    val values: List<Value>
)