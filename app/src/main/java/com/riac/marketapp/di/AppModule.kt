package com.riac.marketapp.di

import android.app.Application
import com.riac.marketapp.common.Constants.Companion.BASE_URL
import com.riac.marketapp.common.DispatcherProvider
import com.riac.marketapp.data.local.AppDao
import com.riac.marketapp.data.local.AppDatabase
import com.riac.marketapp.data.remote.MarketAPI
import com.riac.marketapp.data.repository.MarketRepositoryImpl
import com.riac.marketapp.domain.repository.MarketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getAppDB(context: Application): AppDatabase {
        return AppDatabase.getAppDB(context)
    }

    @Singleton
    @Provides
    fun getDao(appDB: AppDatabase): AppDao {
        return appDB.getDAO()
    }

    @Singleton
    @Provides
    fun provideDataAPI(): MarketAPI =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build().create(MarketAPI::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(api: MarketAPI,db: AppDatabase): MarketRepository = MarketRepositoryImpl(api,db.getDAO())

    @Singleton
    @Provides
    fun providesDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}