package com.put.tsm.whour.data.repository.dataSource

import com.put.tsm.whour.data.models.Category
import com.put.tsm.whour.data.models.CategoryItem
import com.put.tsm.whour.data.repository.dataSource.local.LocalDataSource
import com.put.tsm.whour.data.repository.dataSource.remote.RemoteDataSource
import com.put.tsm.whour.data.utils.NetworkUtils.retry
import com.put.tsm.whour.data.utils.Result
import javax.inject.Inject

class BaseDataSourceImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : BaseDataSource {
    override suspend fun getAllCategories(forceUpdate: Boolean): Result<List<Category>> {
        return if (forceUpdate) {
            when (val response = remoteDataSource.getAllCategories()) {
                is Result.Success -> response.also {
                    localDataSource.saveCategories(it.data)
                }
                is Result.Error -> retry {
                    remoteDataSource.getAllCategories()
                }
            }
        } else {
            when (val response = localDataSource.getAllCategories()) {
                is Result.Success -> response
                is Result.Error -> retry {
                    remoteDataSource.getAllCategories()
                }
            }
        }
    }

    override suspend fun getAllItems(forceUpdate: Boolean): Result<List<CategoryItem>> {
        return if (forceUpdate) {
            when (val response = remoteDataSource.getAllItems()) {
                is Result.Success -> response.also {
                    localDataSource.saveAllItems(it.data)
                }
                is Result.Error -> retry {
                    remoteDataSource.getAllItems()
                }
            }
        } else {
            when (val response = localDataSource.getAllItems()) {
                is Result.Success -> response
                is Result.Error -> retry {
                    remoteDataSource.getAllItems()
                }
            }
        }
    }

    override suspend fun getItemsFromCategory(
        categoryId: String,
        forceUpdate: Boolean
    ): Result<List<CategoryItem>> {
        return when {
            forceUpdate -> remoteDataSource.getItemsFromCategory(categoryId)
            else -> when (val response = localDataSource.getItemsFromCategory(categoryId)) {
                is Result.Success -> response
                is Result.Error -> retry {
                    remoteDataSource.getItemsFromCategory(categoryId, forceUpdate = true)
                }
            }
        }
    }

    override suspend fun saveQuiz(categoryId: String, winnerType: String) {
        localDataSource.saveQuiz(categoryId, winnerType)
    }
}