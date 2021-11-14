package com.put.tsm.whour.data.repository.datastore

import com.put.tsm.whour.data.models.Gender
import com.put.tsm.whour.data.models.QuizAnswer
import com.put.tsm.whour.data.models.User
import kotlinx.coroutines.flow.Flow

interface QuizDataStore {
    suspend fun saveQuiz(categoryId: String, winnerType: String)
    suspend fun clear()
    suspend fun init()
    suspend fun login(name: String, age: Int, gender: Gender)
    suspend fun logout()
    val completedQuizzesFlow: Flow<List<QuizAnswer>>
    val userFlow: Flow<User?>
}