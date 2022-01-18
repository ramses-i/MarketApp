package com.riac.marketapp.data.remote.dto.query

data class ValueX(
    val id: String,
    val name: String,
    val path_from_root: List<PathFromRoot>
)