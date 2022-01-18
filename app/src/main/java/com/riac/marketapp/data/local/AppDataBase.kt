package com.riac.marketapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.riac.marketapp.data.local.entities.ItemEnt

@Database(entities = [ItemEnt::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getDAO(): AppDao

    companion object {
        private var dbINSTANCE: AppDatabase? = null

        fun getAppDB(context: Context): AppDatabase{
            if(dbINSTANCE == null) {
                dbINSTANCE = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "MarketAppDB"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return dbINSTANCE!!
        }
    }
}