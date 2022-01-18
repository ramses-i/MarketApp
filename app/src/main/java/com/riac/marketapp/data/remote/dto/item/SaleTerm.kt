package com.riac.marketapp.data.remote.dto.item

data class SaleTerm(
    val id: String,
    val name: String,
    val value_id: Any,
    val value_name: String,
    val value_struct: ValueStruct,
    val values: List<ValueX>
)