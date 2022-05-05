package Kakao_Internship_2021.`미로 탈출`

import java.util.*
import kotlin.collections.HashMap

const val INF = 1_000_000_000
class Solution {
    lateinit var isVisited: Array<BooleanArray> // state[node][state]
    lateinit var matrix: Array<IntArray> // 인접 리스트
    fun solution(n: Int, start: Int, end: Int, roads: Array<IntArray>, traps: IntArray): Int {
        var answer: Int = 0
        matrix = Array(n + 1) { IntArray(n + 1) { INF } }
        isVisited = Array(n + 1) { BooleanArray(1 shl traps.size) }
        for (road in roads) {
            val (start, end, weight) = road
            if (matrix[start][end] > weight) {
                matrix[start][end] = weight
            }
        }

        return dijkstra(start, end, traps)
    }

    fun dijkstra(start: Int, end: Int, traps: IntArray) : Int {
        val pq = PriorityQueue<Node>()
        pq.offer(Node(start, 0, 0))

        while(pq.isNotEmpty()) {
            val curNode = pq.poll()
            var state = curNode.state

            if (curNode.node == end) {
                return curNode.weight
            }

            if (isVisited[curNode.node][curNode.state]) continue
            isVisited[curNode.node][curNode.state] = true

            val map = HashMap<Int, Boolean>() // 현재 트랩이 켜져있는 곳만 담김
            var curTrapState = false

            traps.forEachIndexed { i, trap ->
                val bit = 1 shl i
                // i번째 함정의 비트가 켜져 있을 때.
                if (state and bit != 0) {

                    // 현재 밟고 있는 노드의 함정정보를 꺼준다.
                    if (trap == curNode.node) {
                        state = state and bit.inv()
                    } else {
                        map[trap] = true
                    }
                    // i 번째 함정의 비트가 꺼져 있을 때.
                } else {
                    // 현재 밟고 있는 노드의 함정정보를 켜준다.
                    if (trap == curNode.node) {
                        state = state or bit
                        map[trap] = true
                        curTrapState = true
                    }
                }
            }
            // 현재 state를 갱신하고 다음 노드를 queue에 넣는다.
            for (next in matrix[curNode.node].indices) {
                if (curNode.node == next) continue
                val nextTrapState = map.containsKey(next)

                // 현재와 다음 노드의 함정 상태가 같다면 도로의 방향은 원점인 것
                if (curTrapState == nextTrapState) {
                    if (matrix[curNode.node][next] != INF) {
                        pq.offer(Node(next, curNode.weight + matrix[curNode.node][next], state))
                    }

                } else {
                    if (matrix[next][curNode.node] != INF) {
                        pq.offer(Node(next, curNode.weight + matrix[next][curNode.node], state))
                    }
                }
            }
        }


        return INF
    }
}
data class Node(val node: Int, val weight: Int, val state: Int) : Comparable<Node> {
    override fun compareTo(other: Node) : Int  = weight - other.weight
}
