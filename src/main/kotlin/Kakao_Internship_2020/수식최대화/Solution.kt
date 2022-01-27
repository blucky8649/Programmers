package Kakao_Internship_2020.수식최대화

import java.lang.IllegalArgumentException
import java.util.*
import kotlin.math.abs

class Solution {
    fun solution(expression: String): Long {
        var answer : Long = 0

        val priority = arrayOf(
            arrayOf('+', '-', '*'),
            arrayOf('+', '*', '-'),
            arrayOf('-', '+', '*'),
            arrayOf('-', '*', '+'),
            arrayOf('*', '+', '-'),
            arrayOf('*', '-', '+')
        )

        for (case in priority) {
            println(Arrays.toString(case))
            answer = dfs(expression, 0, case).let { if(abs(it) > answer) {
                abs(it)
            } else answer }
        }

        return answer
    }

    fun dfs(expression : String, depth : Int, case : Array<Char>) : Long {
        if (expression.isLong()) return expression.toLong()
        val tokens = expression.split(case[depth])
        var result : Long? = null
        for (token in tokens) {
            val value = dfs(token, depth + 1, case)
            result = if (result == null) {
                value
            } else {
                calc(result, value, case[depth])
            }
        }
        return result!!
    }
}
fun String.isLong(): Boolean {
    return try {
        this.toLong()
        true
    } catch (e: Exception) {
        false
    }
}
fun calc(a : Long, b : Long, op : Char) : Long {
    return when (op) {
        '+' -> a + b
        '-' -> a - b
        '*' -> a * b
        else -> throw IllegalArgumentException()
    }
}