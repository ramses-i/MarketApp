package com.riac.marketapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.riac.marketapp.domain.model.Item

@Entity(tableName = "item")
class ItemEnt(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "mercadolibre_id") val mercadolibre_id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "currency_id") val currency_id: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String
)

fun ItemEnt.toItem(): Item {
    return Item(mercadolibre_id, title, price, currency_id, thumbnail)
}