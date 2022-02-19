package Kakao_Blind_Recruitment_2021.합승_택시_요금

import java.util.*
import kotlin.math.*
const val INF = 1_000_000_000

class Solution {
    private lateinit var dst: IntArray
    private lateinit var list: Array<ArrayList<Node>>
    fun solution(n: Int, s: Int, a: Int, b: Int, fares: Array<IntArray>): Int {
        var answer: Int = Integer.MAX_VALUE
        list = Array(n + 1) { ArrayList<Node>() }

        // 인접리스트 채우기
        for (i in fares.indices) {
            val (start, end, weight) = fares[i].map { it }

            list[start].add(Node(end, weight))
            list[end].add(Node(start, weight))
        }
        val dstS = dijkstra(s, n)
        val dstA = dijkstra(a, n)
        val dstB = dijkstra(b, n)

        for (i in 1 .. n) {
            // 갈 수 없는 곳이면 continue
            if (dstS[i] == INF || dstA[i] == INF || dstB[i] == INF) continue
            // S, A, B 각각 i지점까지 오는 최단거리는 i지점까지 합승하고 A, B 따로 가는 거리와 같다.
            answer = min(answer, dstS[i] + dstA[i] + dstB[i])
        }
        return answer
    }

    fun dijkstra(start: Int, n: Int): IntArray {
        val q = PriorityQueue<Node>()
        val isVisited = BooleanArray(n + 1)
        dst = IntArray(n + 1) {i -> INF}
        dst[start] = 0
        q.offer(Node(start, 1))

        while (q.isNotEmpty()) {
            val cur = q.poll()
            if (isVisited[cur.end]) continue
            isVisited[cur.end] = true

            for (next in list[cur.end]) {
                if (dst[next.end] > dst[cur.end] + next.weight) {
                    dst[next.end] = dst[cur.end] + next.weight
                    q.offer(Node(next.end, dst[next.end]))
                }
            }
        }
        return dst
    }
}
data class Node(val end: Int, val weight: Int) : Comparable<Node> {
    override fun compareTo(other: Node) = weight - other.weight
}