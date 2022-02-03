package Kakao_Internship_2021.숫자_문자열과_영단어

class Solution {
    // 안녕하세요
    fun solution(s: String): Int {
        val nums = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven","eight", "nine")
        var str = s
        for (i in 0 until nums.size) {
            str = str.replace(nums[i], i.toString())
        }
        return str.toInt()
    }
}