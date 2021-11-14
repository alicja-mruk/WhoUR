package com.put.tsm.whour.data.repository.datastore


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.put.tsm.whour.data.models.Gender
import com.put.tsm.whour.data.models.QuizAnswer
import com.put.tsm.whour.data.models.QuizAnswerList
import com.put.tsm.whour.data.models.User
import com.put.tsm.whour.data.utils.jsonToObjectOrNull
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.util.*
import javax.inject.Inject


class QuizDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi
) :
    QuizDataStore {
    private val dataStore = context.dataStore
    private val quizAnswerListAdapter = moshi.adapter(QuizAnswerList::class.java)
    private val userAdapter = moshi.adapter(User::class.java)

    private val completedQuizzes = MutableStateFlow<List<QuizAnswer>>(emptyList())
    override val completedQuizzesFlow: Flow<List<QuizAnswer>> = completedQuizzes

    private val user = MutableStateFlow<User?>(null)
    override val userFlow: Flow<User?> = user

    override suspend fun saveQuiz(categoryId: String, winnerType: String) {
        val isPresent = completedQuizzes.value.find { it.categoryId == categoryId } != null
        val quizAnswer = QuizAnswer(categoryId, winnerType)
        addCompletedQuiz(isPresent, quizAnswer)
    }

    private suspend fun addCompletedQuiz(isPresent: Boolean, quizAnswer: QuizAnswer?) {
        dataStore.edit { preferences ->
            val updatedQuizList: List<QuizAnswer>
            try {
                updatedQuizList = if (!isPresent) {
                    completedQuizzes.value + listOfNotNull(quizAnswer)
                } else {
                    val quizListWithoutCurrent =
                        completedQuizzes.value.filter { it.categoryId != quizAnswer?.categoryId }
                    quizListWithoutCurrent + listOfNotNull(quizAnswer)
                }
                val newQuizListJson =
                    quizAnswerListAdapter.toJson(QuizAnswerList(quizList = updatedQuizList))
                preferences[COMPLETED_QUIZZES_KEY] = newQuizListJson
                completedQuizzes.emit(updatedQuizList)
            } catch (e: Throwable) {
                throw Exception(e)
            }
        }
    }

    private suspend fun getCompletedQuizzes(): List<QuizAnswer>? = dataStore.data.catch {
        if (it is IOException) emit(emptyPreferences())
        else throw Exception(it.message)
    }.map {
        val completedQuizzesJson = it[COMPLETED_QUIZZES_KEY] ?: return@map null
        if (completedQuizzesJson.isEmpty()) return@map null
        moshi.jsonToObjectOrNull<List<QuizAnswer>>(completedQuizzesJson)
    }.firstOrNull()

    private suspend fun getCurrentUser(): User? = dataStore.data.catch {
        if (it is IOException) emit(emptyPreferences())
        else throw Exception(it.message)
    }.map {
        val userJson = it[USER_KEY] ?: return@map null
        if (userJson.isEmpty()) return@map null
        moshi.jsonToObjectOrNull<User>(userJson)
    }.firstOrNull()

    override suspend fun clear() {
        dataStore.edit { preferences ->
            try {
                val emptyList: List<QuizAnswer> = QuizAnswerList(quizList = emptyList()).quizList
                val newQuizListJson =
                    quizAnswerListAdapter.toJson(QuizAnswerList(quizList = emptyList))
                preferences[COMPLETED_QUIZZES_KEY] = newQuizListJson
                completedQuizzes.emit(emptyList())
            } catch (e: Throwable) {
                throw Exception(e)
            }
        }
    }

    override suspend fun init() {
        completedQuizzes.value = getCompletedQuizzes() ?: emptyList()
        user.value = getCurrentUser()
    }

    override suspend fun login(name: String, age: Int, gender: Gender) {
        val currentUser = User(id = generateRandomId(), name = name, age = age, gender = gender)
        dataStore.edit { preferences ->
            try {
                val currentUserJson = userAdapter.toJson(currentUser)
                preferences[USER_KEY] = currentUserJson
                user.emit(currentUser)
            } catch (e: Throwable) {
                throw Exception(e)
            }
        }
    }

    override suspend fun logout() {
        dataStore.edit { preferences ->
            try {
                preferences[USER_KEY] = ""
                user.emit(null)
            } catch (e: Throwable) {
                throw Exception(e)
            }
        }
    }

    private fun generateRandomId(): String = UUID.randomUUID().toString().substring(0, 15)

    private companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "quiz_data_store")
        val COMPLETED_QUIZZES_KEY = stringPreferencesKey("completed_quizzes")
        val USER_KEY = stringPreferencesKey("user")
    }
}