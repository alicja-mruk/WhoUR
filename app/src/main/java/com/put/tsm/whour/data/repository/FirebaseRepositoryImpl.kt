package com.put.tsm.whour.data.repository

import com.put.tsm.whour.data.models.Category
import com.put.tsm.whour.data.models.CategoryItem
import com.put.tsm.whour.data.models.QuizAnswer
import com.put.tsm.whour.data.models.User
import com.put.tsm.whour.data.repository.dataSource.BaseDataSource
import com.put.tsm.whour.data.repository.datastore.QuizDataStore
import com.put.tsm.whour.data.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val dataSource: BaseDataSource,
    dataStore: QuizDataStore
) :
    FirebaseRepository {
    override suspend fun getAllCategories(forceUpdate: Boolean): Result<List<Category>> =
        dataSource.getAllCategories(forceUpdate)

    override suspend fun getAllItems(forceUpdate: Boolean): Result<List<CategoryItem>> =
        dataSource.getAllItems(forceUpdate)

    override suspend fun getItemsFromCategory(
        categoryId: String,
        forceUpdate: Boolean
    ): Result<List<CategoryItem>> = dataSource.getItemsFromCategory(categoryId, forceUpdate)

    override suspend fun saveQuiz(categoryId: String, winnerType: String){
        dataSource.saveQuiz(categoryId, winnerType)
    }

    override val completedQuizzesFlow: Flow<List<QuizAnswer>> = dataStore.completedQuizzesFlow
    override val userFlow: Flow<User?> = dataStore.userFlow
}