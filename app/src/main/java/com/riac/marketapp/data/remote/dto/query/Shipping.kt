package com.riac.marketapp.data.remote.dto.query

data class Shipping(
    val free_shipping: Boolean,
    val logistic_type: String,
    val mode: String,
    val store_pick_up: Boolean,
    val tags: List<String>
)