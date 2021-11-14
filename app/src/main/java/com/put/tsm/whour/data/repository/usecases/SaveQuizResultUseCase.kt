package com.put.tsm.whour.data.repository.usecases

import com.put.tsm.whour.data.models.CategoryItem
import com.put.tsm.whour.data.repository.FirebaseRepository
import com.put.tsm.whour.data.utils.Result
import javax.inject.Inject

class SaveQuizResultUseCase @Inject constructor(private val repository: FirebaseRepository) {
    suspend operator fun invoke(categoryId: String, winnerType: String) {
        repository.saveQuiz(categoryId, winnerType)
    }
}