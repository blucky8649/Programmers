package Kakao_Blind_Recruitment_2020.외벽_점검

class Solution {
    var finish = false
    fun solution(n: Int, weak: IntArray, dist: IntArray): Int {
        var answer = 0
        dist.sort() // 그리디한 결과값을 얻기 위한 정렬

        // 로직 설명: 인원 수를 한 명씩 투입하여 백트래킹을 통해 가능한지 판별하고 가능하면 return 후 종료.

        for (i in dist.indices) {
            val route = ArrayList<Route>() // 점검한 route를 중복없이 표시하기 위한 set
            val check = BooleanArray(weak.size)
            permutation(0, 0, i, check, route, weak, dist)
            if (finish) return i
        }

        return answer
    }

    fun permutation(
        start: Int,
        depth: Int,
        people: Int,
        check: BooleanArray,
        route: ArrayList<Route>,
        weak: IntArray,
        dist: IntArray
    ) {
        if (finish) return
        if (depth == people) {
            val set = HashSet<Int>()
            for (w in weak) {
                for (r in route) {
                    if (w in r.start..r.end) {
                        set.add(w)
                        break
                    }
                }
            }
            if (set.size == weak.size) {
                finish = true
            }
            return
        }

        for (i in start until weak.size) {
            if (check[i]) continue
            check[i] = true
            route.add(Route(i, i + dist[depth]))
            permutation(i + 1, depth + 1, people, check, route, weak, dist)
            check[i] = false
            route.removeAt(route.size - 1)
        }
    }
}

data class Route(val start: Int, val end: Int)