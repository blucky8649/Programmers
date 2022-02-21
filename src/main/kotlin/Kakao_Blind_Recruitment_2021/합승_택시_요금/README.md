[문제 풀러 가기!](https://programmers.co.kr/learn/courses/30/lessons/72413)

## 풀이

최단거리 문제입니다. `다익스트라`로 해결하였습니다.

이 문제에서 중요한 것은 A와 B는 특점 지점까지만 합승하고 남은 길은 따로 가는것이 포인트입니다. 그러나 역으로 생각해보면 ** S, A, B 각각의 지점이 특점 지점까지 모이는 거리와 같은 의미가 됩니다.** 

### ![](https://images.velog.io/images/blucky8649/post/57564e25-2850-4fee-961a-a440fdc894e5/image.png)

만약 위 그림과 같이 2 지점까지만 합승하고 A와 B는 따로 가는것이 최단거리라 가정해봅시다.

이 것은 결국 **S, A, B 각각의 지점에서 2지점까지 가는 최소거리들의 합** 이 됩니다.

코드 올려드리겠습니다.
## Source Code
```kotlin
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
```
