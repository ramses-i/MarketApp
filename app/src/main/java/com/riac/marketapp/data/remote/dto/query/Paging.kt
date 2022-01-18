package com.riac.marketapp.data.remote.dto.query

data class Paging(
    val limit: String,
    val offset: String,
    val primary_results: String,
    val total: String
)