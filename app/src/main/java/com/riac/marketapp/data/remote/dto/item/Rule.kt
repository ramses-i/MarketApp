package com.riac.marketapp.data.remote.dto.item

data class Rule(
    val default: Boolean,
    val free_mode: String,
    val free_shipping_flag: Boolean,
    val value: Any
)