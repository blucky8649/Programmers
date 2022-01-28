[![zzz](https://media.vlpt.us/images/blucky8649/post/896e4ff8-6303-4a9a-89d2-93b17f7c84a1/%EC%8D%B8%EB%84%A4%EC%9D%B4%EB%A3%A8_%EB%B3%B5%EC%82%AC%EB%B3%B8-001.png)](https://programmers.co.kr/learn/courses/30/lessons/67257)
[문제풀러가기!](https://programmers.co.kr/learn/courses/30/lessons/67257)

## 풀이

split 메서드를 이용하여 재귀적으로 계속 쪼개가면서 우선순위에 맞게 완전탐색 해주면 된다.

```kotlin
val priority = arrayOf(
	arrayOf('+', '-', '*'),
	arrayOf('+', '*', '-'),
	arrayOf('-', '+', '*'),
	arrayOf('-', '*', '+'),
	arrayOf('*', '+', '-'),
	arrayOf('*', '-', '+')
)
```
연산에는 `+`, `*`, `-` 만 이용하여 우선순위를 구하면 되므로 이렇게 바로 2차원 배열로 선언해주면 된다.


```kotlin
fun dfs(expression : String, depth : Int, case : Array<Char>) : Long {
	// 쪼개진 expression 문자열이 Long으로 변환될 수 있으면 더 이상 쪼개지 않고 변환된 수를 반환한다.
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
```

이 코드를 해석하려면 내부적으로 어떻게 재귀호출되는지 알아야 한다.

예를 들어, `100-200*300-500+20` 이라는 식이 있다고 하자. 이 식을 `*` > `+` > `-` 의 우선순위를 두고 재귀호출 해보자. (case{`-`, `+`, `*`})

1. 처음, `-`을 기준으로 split을 하면 (100)  (200*300)  (500+20) 으로 나눠 진다.
2. 각각의 괄호에 대해 다시 재귀호출을 하면 (100) (60000) (520) 이 되고 차례대로 빼주면 -60420 이 된다.

## Source Code
```kotlin
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
```
