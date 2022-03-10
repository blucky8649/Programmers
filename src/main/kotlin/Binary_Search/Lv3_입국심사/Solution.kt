package Binary_Search.Lv3_입국심사

class Solution {
    fun solution(n: Int, times: IntArray): Long {
        var min: Long = 0
        var max: Long = (1_000_000_000 * n.toLong())

        while (min < max) {
            val mid = (min + max) / 2
            // count: mid 시간 안에 검사 받을 수 있는 최대 인원
            var count: Long = 0
            for (time in times) {
                // cap: 한 심사위원이 mid시간 내에 할당할 수 있는 최대 인원
                val cap: Long = mid / time
                count += cap
            }
            if (count >= n) {
                // 만약, 최대 심사할 수 있는 인원을 충족하였다 해도, 최대한 시간을 단축해야 한다.
                max = mid
            } else {
                min = mid + 1
            }
        }
        return min
    }
}