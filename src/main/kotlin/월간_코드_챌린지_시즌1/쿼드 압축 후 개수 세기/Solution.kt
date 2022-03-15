package 월간_코드_챌린지_시즌1.`쿼드 압축 후 개수 세기`

class Solution {
    private lateinit var a: Array<IntArray>
    private lateinit var answer: IntArray
    fun solution(arr: Array<IntArray>): IntArray {
        a = arr
        answer = IntArray(2)
        count(0, 0, a.size)
        return answer
    }
    private fun count(row: Int, col: Int, size: Int) {
        if (check(row, col, size)) {
            if (a[row][col] == 0) {
                answer[0]++
            } else {
                answer[1]++
            }
            return
        }
        val newSize = size / 2

        count(row, col, newSize)
        count(row + newSize, col, newSize)
        count(row, col + newSize, newSize)
        count(row + newSize, col + newSize, newSize)
    }
    private fun check(row: Int, col: Int, size: Int): Boolean {
        for (i in row until row + size) {
            for (j in col until col + size) {
                if (a[i][j] != a[row][col])
                    return false
            }
        }
        return true
    }
}