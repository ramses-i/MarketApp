package com.riac.marketapp.data.local

import com.riac.marketapp.data.local.entities.ItemEnt
import javax.inject.Inject

class RoomRepository @Inject constructor(private val appDao: AppDao) {

    fun getLastFiveRecords(): List<ItemEnt> {
        return appDao.getLastViewedItems()
    }

    fun insertRecord(item: ItemEnt) {
        appDao.updateLastViewedItem(item)
    }
}