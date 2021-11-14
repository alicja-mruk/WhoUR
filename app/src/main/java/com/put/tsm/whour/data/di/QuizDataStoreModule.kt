package com.put.tsm.whour.data.di

import android.content.Context
import com.put.tsm.whour.data.repository.datastore.QuizDataStore
import com.put.tsm.whour.data.repository.datastore.QuizDataStoreImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object QuizDataStoreModule {

    @Provides
    fun providesQuizDataStore(@ApplicationContext context: Context, moshi: Moshi): QuizDataStore =
        QuizDataStoreImpl(context, moshi)
}