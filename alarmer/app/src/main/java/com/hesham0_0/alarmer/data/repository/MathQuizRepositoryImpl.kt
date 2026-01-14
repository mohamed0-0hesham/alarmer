package com.hesham0_0.alarmer.data.repository

import android.content.Context
import com.hesham0_0.alarmer.domain.model.MathProblem
import com.hesham0_0.alarmer.domain.repository.MathQuizRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import javax.inject.Inject

class MathQuizRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MathQuizRepository {

    override suspend fun getMathProblems(): List<MathProblem> = withContext(Dispatchers.IO) {
        val jsonString = context.assets.open("math_problems.json").bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)
        val problems = mutableListOf<MathProblem>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            problems.add(
                MathProblem(
                    problem = obj.getString("problem"),
                    answer = obj.getString("answer")
                )
            )
        }
        problems.shuffled().take(10)
    }
}
