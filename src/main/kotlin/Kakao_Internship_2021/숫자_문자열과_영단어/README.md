![썸네이루_복사본-001](https://user-images.githubusercontent.com/83625797/152463215-61560ca1-b6f4-49dd-a56e-70ec5a9f45a3.png)
[문제 풀러 가기 !](https://programmers.co.kr/learn/courses/30/lessons/81301)

## 풀이
이번 달 목표는 카카오 기출문제를 레벨 3까지 전부 코틀린으로 풀어보는 것이다.
물론 자바로는 거의 다 풀었었지만 처음 풀 때의 기분을 되새기며 처음부터 차근차근 코틀린으로 풀어나가고 있다.

각설하고 문제 풀이는 각각의 소문자로 구성된 숫자 영단어가 담긴 String 배열을 만든 다음, 배열을 탐색하면서 s에 담긴 영단어를 숫자로 바꿔주기만 하면 된다.

## Source code
```kotlin
package Kakao_Internship_2021.숫자_문자열과_영단어

class Solution {
    fun solution(s: String): Int {
        val nums = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven","eight", "nine")
        var str = s
        for (i in 0 until nums.size) {
            str = str.replace(nums[i], i.toString())
        }
        return str.toInt()
    }
}
```

