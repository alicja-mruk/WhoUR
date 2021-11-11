package com.put.tsm.whour.data.repository.usecases

import com.put.tsm.whour.data.models.Category
import com.put.tsm.whour.data.repository.FirebaseRepository
import com.put.tsm.whour.data.utils.Result
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(private val repository: FirebaseRepository) {
    suspend operator fun invoke(forceUpdate: Boolean = false): Result<List<Category>> =
        repository.getAllCategories(forceUpdate)
}