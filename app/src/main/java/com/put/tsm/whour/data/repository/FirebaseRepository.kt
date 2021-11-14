package com.put.tsm.whour.data.repository

import com.put.tsm.whour.data.models.*
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
    suspend fun login(name: String, age: Int, gender: Gender)
}