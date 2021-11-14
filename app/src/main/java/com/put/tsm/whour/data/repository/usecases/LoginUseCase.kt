package com.put.tsm.whour.data.repository.usecases

import com.put.tsm.whour.data.models.CategoryItem
import com.put.tsm.whour.data.models.Gender
import com.put.tsm.whour.data.repository.FirebaseRepository
import com.put.tsm.whour.data.utils.Result
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: FirebaseRepository) {
    suspend operator fun invoke(name: String, age: Int, gender: Gender) {
        repository.login(name, age, gender)
    }
}