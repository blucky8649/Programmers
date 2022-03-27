package MST

import java.util.*

class Solution {
    lateinit var parents: IntArray
    fun solution(n: Int, costs: Array<IntArray>): Int {
        parents = IntArray(n + 1) { i -> i }
        val q = PriorityQueue<Node>()
        for (item in costs) {
            val (start, end, weight) = item.map { it }
            q.offer(Node(start, end, weight))
        }
        var answer = 0
        var cnt = 0

        while (q.isNotEmpty()) {
            val curNode = q.poll()
            val a = find(curNode.start)
            val b = find(curNode.end)

            if (a == b) continue
            union(a, b)
            cnt++
            answer += curNode.weight
            if (cnt == n - 1) break
        }
        return answer
    }
    fun find(x: Int): Int {
        if (parents[x] == x) {
            return x
        }
        parents[x] = find(parents[x])
        return parents[x]
    }
    fun union(a: Int, b: Int) {
        val A = find(a)
        val B = find(b)

        if (A == B) return
        parents[B] = A
    }
}
data class Node(val start: Int, val end: Int, val weight: Int): Comparable<Node> {
    override fun compareTo(other: Node) = weight - other.weight
}