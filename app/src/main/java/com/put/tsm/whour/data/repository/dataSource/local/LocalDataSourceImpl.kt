package com.put.tsm.whour.data.repository.dataSource.local

import com.put.tsm.whour.data.models.Category
import com.put.tsm.whour.data.models.CategoryItem
import com.put.tsm.whour.data.repository.exceptions.NoCategoriesInCacheException
import com.put.tsm.whour.data.repository.exceptions.NoCategoryItemsInCacheException
import com.put.tsm.whour.data.utils.Result
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor() : LocalDataSource {
    private var categories: List<Category> = emptyList()
    private var categoryItems: List<CategoryItem> = emptyList()

    override suspend fun getAllCategories(forceUpdate: Boolean): Result<List<Category>> {
        return if (categories.isEmpty()) {
            Result.Error(NoCategoriesInCacheException())
        } else {
            Result.Success(categories)
        }
    }

    override suspend fun getAllItems(forceUpdate: Boolean): Result<List<CategoryItem>> {
        return if (categoryItems.isEmpty()) {
            Result.Error(NoCategoryItemsInCacheException())
        } else {
            Result.Success(categoryItems)
        }
    }

    override suspend fun getItemsFromCategory(
        categoryId: String,
        forceUpdate: Boolean
    ): Result<List<CategoryItem>> {
        val items = categoryItems.filter { it.categoryId == categoryId }
        if (items.isEmpty()) return Result.Error(NoCategoryItemsInCacheException())
        return Result.Success(items)
    }

    override fun saveCategories(categories: List<Category>) {
        this.categories = categories
    }

    override fun saveAllItems(items: List<CategoryItem>) {
        this.categoryItems = items
    }
}