package com.put.tsm.whour.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuizAnswerList(@Json(name = "quizList") val quizList: List<QuizAnswer>)
