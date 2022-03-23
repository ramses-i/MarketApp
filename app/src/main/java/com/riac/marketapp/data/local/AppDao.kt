package com.riac.marketapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.riac.marketapp.data.local.entities.ItemEnt

@Dao
interface AppDao {

    @Query("SELECT * FROM item GROUP BY mercadolibre_id ORDER BY id DESC LIMIT 6")
    fun getLastViewedItems(): List<ItemEnt>

    @Insert
    fun updateLastViewedItem(itemEntity: ItemEnt)
}