package Kakao_Blind_Recrouitment_2022.양과_늑대

import java.util.*
import kotlin.math.max

class Solution {
    lateinit var INFO: IntArray
    lateinit var list: Array<ArrayList<Int>> // 인접 리스트
    var answer: Int = 0
    fun solution(info: IntArray, edges: Array<IntArray>): Int {
        INFO = info

        list = Array(info.size) { ArrayList<Int>()}
        for (edge in edges) {
            val start = edge[0]
            val end = edge[1]
            list[start].add(end)
        }
        val route: Queue<Int> = LinkedList<Int>()
        dfs(0, 0, 0, route)
        return answer
    }
    /**
     * @param cur: 현재 노드
     * @param sheep, wolf: 양, 늑대 수
     * @parem route: 방문해야할 노드
     */
    fun dfs(cur: Int, sheep: Int, wolf: Int, route: Queue<Int>) {
        // 1. 현재 양, 늑대의 수 갱신
        val ns = sheep + (INFO[cur] xor 1) // XOR연산, info값이 0일 때, 양의 개수 1 증가
        val nw = wolf + INFO[cur]

        // 2. 만약 (양 <= 늑대라면)
        if (nw >= ns) return // 현재 노드는 갈 수 없는 노드이므로 종료


        // 3. 그렇지 않다면
        answer = max(answer, ns) // 양의 최대값 갱신

        val nextNode: Queue<Int> = LinkedList<Int>(route)
        // 4. 다음 노드 리스트에 현재 노드의 자식노드들을 추가
        for (node in list[cur]) {
            nextNode.offer(node)
        }

        val size = nextNode.size
        //반복문 시작 (다음 노드 리스트의 사이즈 만큼)
        repeat(size) {
            // ㄱ. 방문하려는 노드는 리스트에서 없애준다.
            val next = nextNode.poll()
            // ㄴ. 재귀 호출 DFS(다음노드, 갱신된 양, 갱신된 늑대, 다음노드 리스트)
            dfs(next, ns, nw, nextNode)
            // ㄷ. 다시 리스트에 아까 방문한 노드를 추가시키고 다음 노드를 방문할 준비를 한다
            nextNode.offer(next)
        } // 반복문 종료
    }
}