package com.put.tsm.whour.data.di

import com.put.tsm.whour.data.repository.FirebaseRepository
import com.put.tsm.whour.data.repository.FirebaseRepositoryImpl
import com.put.tsm.whour.data.repository.dataSource.BaseDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseRepositoryModule {

    @Singleton
    @Provides
    fun providesFirebaseRepository(dataSource: BaseDataSource): FirebaseRepository =
        FirebaseRepositoryImpl(dataSource)
}