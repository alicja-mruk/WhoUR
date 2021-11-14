package com.put.tsm.whour.data.repository.datasource.local

import com.put.tsm.whour.data.models.Category
import com.put.tsm.whour.data.models.CategoryItem
import com.put.tsm.whour.data.repository.datasource.BaseDataSource

interface LocalDataSource : BaseDataSource {
    fun saveCategories(categories: List<Category>)
    fun saveAllItems(items: List<CategoryItem>)
}