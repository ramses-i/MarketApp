package com.riac.marketapp.data.remote.dto.item

data class SellerAddress(
    val city: City,
    val country: Country,
    val id: String,
    val search_location: SearchLocation,
    val state: StateX
)