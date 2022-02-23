package Summer_Winter_Coding.스티커_모으기
import kotlin.math.max

const val D1 = 0 // 첫 번째 스티커를 뜯었을 경우
const val D2 = 0 // 두 번째 스티커를 뜯었을 경우
class Solution {
    fun solution(sticker: IntArray): Int {
        val m = sticker.size
        if (m == 1) {
            return sticker[0]
        }
        if (m == 2) {
            return max(sticker[0], sticker[1])
        }
        val dp = Array(2) { IntArray(m) }

        // D1: 첫 번째 스티커를 뜯었을 경우, 두번째 스티커는 뜯지 못한다.
        dp[D1][0] = sticker[0]
        dp[D1][1] = sticker[0]

        // D2: 두 번째 스티커를 뜯었을 경우, 첫 번째 스티커는 뜯지 못한다.
        dp[D2][0] = 0
        dp[D2][1] = sticker[1]
        for (i in 2 until m) {
            dp[D1][i] = max(dp[D1][i - 1], sticker[i] + dp[D1][i - 2])
            dp[D2][i] = max(dp[D2][i - 1], sticker[i] + dp[D2][i - 2])
        }
        // 첫 번째 스티커를 뜯은 경우, 마지막 스티커는 뜯지 못한다.
        dp[D1][m - 1] = dp[D1][m - 2]
        return max(dp[D1][m - 1], dp[D1][m - 1])
    }
}