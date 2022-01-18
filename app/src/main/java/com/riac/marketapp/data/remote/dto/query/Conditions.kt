package com.riac.marketapp.data.remote.dto.query

data class Conditions(
    val context_restrictions: List<String>,
    val eligible: Boolean,
    val end_time: Any,
    val start_time: Any
)