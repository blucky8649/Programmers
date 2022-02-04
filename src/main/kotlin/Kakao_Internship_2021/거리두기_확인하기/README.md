![썸네이루_복사본-001](https://user-images.githubusercontent.com/83625797/152462665-c4b8979f-52ca-4275-84be-c39fc5161886.png)
[문제 풀러 가기!](https://programmers.co.kr/learn/courses/30/lessons/81302/)

## 풀이

각각의 테스트 케이스를 기준으로 모든 대기자간의 거리를 확인 해야 합니다. 파티션이 없었다면 대기자 사이의 거리를 맨허튼거리로 O(대기자 수^2)로 해결할 수 있었지만, 파티션이 있기 때문에 BFS를 수행하여 풀이하였습니다.

특정 대기자를 기준으로 BFS를 수행하며 탐색 깊이를 cnt해야 합니다. 그후 다른 대기자를 만나서 cnt가 2미만이어야 거리두기를 실천한 것이죠.

## Source Code
```kotlin
package Kakao_Internship_2021.거리두기_확인하기

import java.util.*
import kotlin.collections.ArrayList

class Solution {
    var dy = arrayOf(-1, 0, 1, 0)
    var dx = arrayOf(0, 1, 0, -1)
    fun solution(places: Array<Array<String>>): IntArray {
        val arr = ArrayList<Int>()
        /** Intervier를 ArrayList에 좌표상으로 담는다. **/
        for (i in 0 until places.size) {
            val interviewer = ArrayList<Dot>()
            for (j in 0 until places[0].size) {
                for (z in 0 until places[0][0].length) {
                    if (places[i][j][z] == 'P') {
                        interviewer.add(Dot(z, j, 0))
                    }
                }
            }
            /** 모든 Interviewer가 거리두기를 실천하고 있는지 판별 (True, False) **/
            var isIllegal = true
            for (man in interviewer) {
                if (!BFS(man, places[i])) {
                    isIllegal = false
                    break
                }
            }
            arr.add(if (isIllegal) 1 else 0)

        }

        return arr.toIntArray()
    }
    /** BFS : 모든 인터뷰어끼리의 거리를 비교한 후 만약 거리두기를 안지키면 false Return **/
    fun BFS(start : Dot, map : Array<String>) : Boolean {
        val isVisited = Array(5) {BooleanArray(5)}
        val q : Queue<Dot> = LinkedList<Dot>()
        val new_map = Array(5) {Array(5) {""} }

        /** 1차원 String 배열을 2차원 배열로 변환 **/
        for (i in 0 until 5) {
            for (j in 0 until 5) {
                new_map[i][j] = map[i][j].toString()
            }
        }
        q.offer(Dot(start.x, start.y, 0))
        isVisited[start.y][start.x] = true
        new_map[start.y][start.x] = "O"
        while (!q.isEmpty()) {
            val d = q.poll()
            if (new_map[d.y][d.x] == "P") {
                if (d.cnt <= 2) return false
            }
            for (i in 0 until 4) {
                val nx = d.x + dx[i]
                val ny = d.y + dy[i]
                if (!(nx in 0 until 5 && ny in 0 until 5 && !isVisited[ny][nx] && new_map[ny][nx] != "X")) continue
                q.offer(Dot(nx, ny, d.cnt + 1))
                isVisited[ny][nx] = true
            }
        }
        return true
    }
}
data class Dot(val x : Int, val y : Int, val cnt : Int)
```
