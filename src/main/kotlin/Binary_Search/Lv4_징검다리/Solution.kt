package Binary_Search.Lv4_징검다리

import kotlin.math.max

class Solution {
    fun solution(distance: Int, rocks: IntArray, n: Int): Int {
        var answer = 0
        rocks.sort()
        var min = 0
        var max = distance

        while (min < max) {
            val mid = (min + max) / 2
            var prev = 0 // 현재 바위와 비교하기 위한 이전 바위의 위치 정보
            var cnt = 0 // 총 제거할 바위의 수

            // 두 바위 사이의 거리가 mid 보다 작다면 해당 바위를 제거
            for (i in rocks.indices) {
                if (rocks[i] - prev < mid) {
                    // 만약 현재 바위와 이전 바위사이의 거리가 mid 보다 작다면, 제거할 바위 수를 카운트 해준다.
                    cnt++
                } else {
                    // 만약 그게 아니라면 현재 바위를 기준으로 다시 비교를 한다.
                    prev = rocks[i]
                }
            }
            // 이전 바위에서 도착지 까지의 거리도 계산하여 cnt 해준다.
            if (distance - prev < mid) cnt++

            // 만약
            if (cnt <= n) {
                answer = max(answer, mid)
                min = mid + 1
            }
            else {
                max = mid
            }
        }
        return answer
    }
}