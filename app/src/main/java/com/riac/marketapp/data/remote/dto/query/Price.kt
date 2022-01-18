package com.riac.marketapp.data.remote.dto.query

data class Price(
    val amount: String,
    val conditions: Conditions,
    val currency_id: String,
    val exchange_rate_context: String,
    val id: String,
    val last_updated: String,
    val metadata: Metadata,
    val regular_amount: Any,
    val type: String
)