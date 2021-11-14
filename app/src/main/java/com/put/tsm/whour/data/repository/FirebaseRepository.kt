package com.put.tsm.whour.data.repository

import com.put.tsm.whour.data.models.Category
import com.put.tsm.whour.data.models.CategoryItem
import com.put.tsm.whour.data.models.QuizAnswer
import com.put.tsm.whour.data.models.User
import com.put.tsm.whour.data.utils.Result
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    suspend fun getAllCategories(forceUpdate: Boolean = false): Result<List<Category>>
    suspend fun getAllItems(forceUpdate: Boolean = false): Result<List<CategoryItem>>
    suspend fun getItemsFromCategory(
        categoryId: String,
        forceUpdate: Boolean = false
    ): Result<List<CategoryItem>>
    suspend fun saveQuiz(categoryId: String, winnerType: String)
    val completedQuizzesFlow: Flow<List<QuizAnswer>>
    val userFlow: Flow<User?>
}