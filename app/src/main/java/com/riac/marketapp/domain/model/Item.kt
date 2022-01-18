package com.riac.marketapp.domain.model

import com.riac.marketapp.data.local.entities.ItemEnt

data class Item(
    val id: String,
    val title: String,
    val price: String,
    val currency_id: String,
    val thumbnail: String
)

fun Item.toItemEnt(): ItemEnt {
    return ItemEnt(
        mercadolibre_id = id,
        title = title,
        price = price,
        currency_id = currency_id,
        thumbnail = thumbnail
    )
}