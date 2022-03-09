package Kakao_Internship_2019.징검다리_건너기

class Solution {
    fun solution(stones: IntArray, k: Int): Int {
        var min = 0
        var max = 200_000_000
        // 구하고자 하는 값은 `친구들의 최대` 이기 때문에 upper bound 를 써보겠어요.
        while (min < max) {
            val mid = (min + max) / 2

            if (crossBridge(k, mid, stones)) {
                // 만약 건널 수 있으면 mid값을 더 높여봐야한다.
                min = mid + 1
            } else {
                max = mid
            }

        }
        return min - 1
    }

    private fun crossBridge(k: Int, mid: Int, bridge: IntArray): Boolean {
        // mid 만큼의 인원이 건널 수 있는지 확인한다.
        // 한 번에 뛸 수 있는 징검다리가 k를 초과한다면 건널 수 없다.
        var cntZero = 0
        for (item in bridge) {
            if (item - mid < 0) {
                cntZero++ // 모든 사람이 돌을 밟을 수 없음
            } else {
                cntZero = 0 // 모든 사람이 돌을 밟을 수 있음
            }
            // 밟을 수 없는 디딤돌이 연속해서 k개가 된다면, 건널 수 없다.
            if (cntZero == k) {
                return false
            }
        }
        return true
    }
}