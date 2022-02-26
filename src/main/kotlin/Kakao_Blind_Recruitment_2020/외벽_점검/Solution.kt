package Kakao_Blind_Recruitment_2020.외벽_점검

import kotlin.math.*

class Solution {
    companion object {
        lateinit var Weak: IntArray
        lateinit var dst: IntArray
        var answer = Int.MAX_VALUE
        var N = 0
    }

    fun solution(n: Int, weak: IntArray, dist: IntArray): Int {
        N = n
        Weak = weak
        dst = dist
        // 오름차순 정렬
        dst.sort()


        // i는 시작지점을 나타낸다.
        for (i in Weak.indices) {
            solve(1, i, 0)
        }
        return if (answer == Int.MAX_VALUE) -1 else answer
    }

    fun solve(depth: Int, pos: Int, isVisited: Int) {
        // 이미 전체 인원을 투입한 상태이므로 종료한다.
        if (depth > dst.size) return
        if (answer <= depth) return

        var visited = isVisited

        // 1. pos위치에서 dist[dist.size - depth]만큼 간다면 얼마만큼 갈 수 있을 지 작성
        for (i in Weak.indices) {
            // pos 에서 nextposition 만큼 갈 수 있는지 탐색
            val nextPos = (pos + i) % Weak.size

            // 현재 포지션과, 다음 포지션 사이의 거리를 구한다.
            var diff = Weak[nextPos] - Weak[pos];

            // 만약 다음 포지션이 weak의 최대 인덱스를 지나고 다시 돌아왔다면
            if (diff < 0) {
                // diff에서 n만큼을 더 더해주면 된다.
                // ex) n이 12일때, 10지점에서 1지점으로 가려면 -9 + 12 = 3 만큼의 거리가 발생한다.
                diff += N
            }

            // 가장 긴 거리를 가는 사람부터 탐색을 보낸다.
            // nextPos 까지 커버를 못한다면 종료한다.
            if (diff > dst[dst.size - depth]) break


            // 그렇지 않다면 해당 포지션의 방문 비트를 켠다.
            visited = visited or (1 shl nextPos)

        }
        // 만약 모든 지점을 탐색하였으면 최소값을 갱신한다.
        if (visited == (1 shl Weak.size) - 1) {
            answer = min(answer, depth)
            return
        }

        // 모든 지역을 커버하지 못했다면, 다른 사람을 추가적으로 보내야한다.
        for (i in Weak.indices) {
            // 만약 이미 방문한 곳이라면 넘긴다.

            if (visited and (1 shl i) != 0) continue
            solve(depth + 1, i, visited)
        }
    }
}