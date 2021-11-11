package com.put.tsm.whour.data.repository

import com.put.tsm.whour.data.models.Category
import com.put.tsm.whour.data.models.CategoryItem
import com.put.tsm.whour.data.repository.dataSource.BaseDataSource
import com.put.tsm.whour.data.utils.Result
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(private val dataSource: BaseDataSource) :
    FirebaseRepository {
    override suspend fun getAllCategories(forceUpdate: Boolean): Result<List<Category>> =
        dataSource.getAllCategories(forceUpdate)

    override suspend fun getAllItems(forceUpdate: Boolean): Result<List<CategoryItem>> =
        dataSource.getAllItems(forceUpdate)

    override suspend fun getItemsFromCategory(
        categoryId: String,
        forceUpdate: Boolean
    ): Result<List<CategoryItem>> = dataSource.getItemsFromCategory(categoryId, forceUpdate)
}