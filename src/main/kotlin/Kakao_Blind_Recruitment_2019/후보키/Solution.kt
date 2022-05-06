package Kakao_Blind_Recruitment_2019.후보키

class Solution {
    var n = 0
    lateinit var table: Array<Array<String>>
    val minList = ArrayList<Int>()
    fun solution(relation: Array<Array<String>>): Int {
        var answer = 0
        table = relation
        n = relation[0].size
        // 비트를 이용하여 유일성을 만족하는지 식별 0 -> 1 shl relation.size 만큼 반복문을 돈다.
        for (i in 1 until (1 shl n)) {
            if (!checkUnique(i)) continue
            if (!checkMinimum(minList, i)) continue
            minList.add(i)
            answer++
        }
        return answer
    }
    fun checkMinimum(list: ArrayList<Int>, index: Int) : Boolean {
        list.forEach { key ->
            if (key and index == key) {
                return false
            }
        }
        return true
    }
    fun checkUnique(index: Int) : Boolean {
        // 1의 비트가 켜진 곳들이 유일성을 만족하는지 판별
        val list = ArrayList<Int>()
        for (i in 0 until n) {
            val bit = 1 shl i // 0001 0010 0100 1000
            if (index and bit != 0) {
                list.add(i)
            }
        }
        val uniqueSet = HashSet<String>()

        table.forEach { tuple ->
            val sb = StringBuilder()
            list.forEach { index ->
                sb.append(tuple[index]).append("?")
            }
            uniqueSet.add(sb.toString())
        }
        if (uniqueSet.size < table.size) {
            return false
        }
        return true
    }
}