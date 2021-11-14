package com.put.tsm.whour.data.di

import com.put.tsm.whour.data.repository.datasource.BaseDataSource
import com.put.tsm.whour.data.repository.datasource.BaseDataSourceImpl
import com.put.tsm.whour.data.repository.datasource.local.LocalDataSource
import com.put.tsm.whour.data.repository.datasource.local.LocalDataSourceImpl
import com.put.tsm.whour.data.repository.datasource.remote.RemoteDataSource
import com.put.tsm.whour.data.repository.datasource.remote.RemoteDataSourceImpl
import com.put.tsm.whour.data.repository.datastore.QuizDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providesLocalDataSource(dataStore: QuizDataStore): LocalDataSource = LocalDataSourceImpl(dataStore)

    @Provides
    @Singleton
    fun providesRemoteDataSource(): RemoteDataSource = RemoteDataSourceImpl()

    @Provides
    @Singleton
    fun providesBaseDataSource(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): BaseDataSource = BaseDataSourceImpl(remoteDataSource, localDataSource)
}