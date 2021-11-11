package com.put.tsm.whour.data.di

import com.put.tsm.whour.data.repository.dataSource.BaseDataSource
import com.put.tsm.whour.data.repository.dataSource.BaseDataSourceImpl
import com.put.tsm.whour.data.repository.dataSource.local.LocalDataSource
import com.put.tsm.whour.data.repository.dataSource.local.LocalDataSourceImpl
import com.put.tsm.whour.data.repository.dataSource.remote.RemoteDataSource
import com.put.tsm.whour.data.repository.dataSource.remote.RemoteDataSourceImpl
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
    fun providesLocalDataSource(): LocalDataSource = LocalDataSourceImpl()

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