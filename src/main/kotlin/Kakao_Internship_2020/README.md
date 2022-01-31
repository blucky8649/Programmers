[![](https://media.vlpt.us/images/blucky8649/post/69eae1d7-7898-40a7-ae37-c2fbffb57029/%EC%8D%B8%EB%84%A4%EC%9D%B4%EB%A3%A8_%EB%B3%B5%EC%82%AC%EB%B3%B8-001%20(4).png)](https://programmers.co.kr/learn/courses/30/lessons/67259)
[문제 풀러 가기!](https://programmers.co.kr/learn/courses/30/lessons/67259)

## 풀이
(1, 1) 지점에서 (R, C) 지점 까지 가는 비용을 최소화 하는 알고리즘입니다.

따라서 다양한 루트를 통해 오는 모든 경로에 대한 경우의 수를 고려해주어야 합니다.

이 문제에 쓰이는 알고리즘은 BFS혹은 DFS로 모든 경로를 탐색해주어야 합니다.

```kotlin
isVisited = Array(4) {Array (R) {Array (C) {false}} }
```
보통 가중치가 없는 2차원 배열에서의 탐색 알고리즘을 수행할 때 2차원 배열의 isVisited 배열을 선언하는 것이 기본적인데요.
> ** Solved Point 1 : 모든 경우를 고려하라 **
하지만 이 문제는 **방향을 어디로 결정하냐에 따라**서 나오는 결과값은 천차만별이기 때문에 모든 경우의 수를 고려해주어야 합니다.
(만약 2차원 배열로 하면 route가 겹치는 경우가 생김)

따라서 isVisited 배열을 3차원 배열로 선언하고 각각의 방향으로 가는 경우의 수를 모두 구해준다면 소요 비용의 최솟값을 구할 수 있을 것입니다.

> ** Solved Point 2  : 이미 갔었던 길도 다시갈 수 있다**
BFS를 수행하면서 거리의 최소값을 계속 board 배열에 저장해주면서 가야합니다. 일반적으로는 이미 방문했던 길은 가지 않는 것이 정석이나, 만약 **방문했던 거리를 다시 방문해서 비용의 최소를 만들 가능성** (new_cost > map[ny][nx])이 있다면 이 경우는 이미 갔었던 거리라도 Queue에 들어갈 자격이 있습니다.

또 한 가지 주의할 점은 직진은 100원이 추가되는 반면, 코너는 500원이 추가된다고 문제에 나와있습니다. 

하지만 코너를 돌고 작진을 한번 더 해야하기 때문에 BFS를 수행할 경우 600원을 더해주면 됩니다.

## Source Code
```kotlin
package Kakao_Internship_2020.경주로_건설

import java.util.*
import kotlin.math.min

class Solution {
    private lateinit var isVisited : Array<Array<Array<Boolean>>> // isVisited[dir][y][x]
    private lateinit var map : Array<IntArray>
    private val dy = arrayOf(-1, 0, 1, 0)
    private val dx = arrayOf(0, 1, 0, -1)
    private var answer = Integer.MAX_VALUE
    private var R = 0
    private var C = 0
    fun solution(board: Array<IntArray>): Int {
        map = board // static영역에서 꺼내쓰기 위한 얕은복사
        R = map.size ; C = map[0].size

        isVisited = Array(4) {Array (R) {Array (C) {false}} }
        BFS(0, 0)
        return answer
    }

    fun BFS(x : Int, y : Int){
        val q : Queue<Dot> = LinkedList<Dot>()
        q.offer(Dot(x, y, -1, 0))
        /** initialize a Array **/
        isVisited[0][y][x] = true
        isVisited[1][y][x] = true
        isVisited[2][y][x] = true
        isVisited[3][y][x] = true

        while (!q.isEmpty()) {
            val cur = q.poll()

            if (cur.x == C - 1 && cur.y == R - 1) {
                answer = min(answer, cur.sum)
            }

            for (i in 0 until 4) {
                val nx = cur.x + dx[i]
                val ny = cur.y + dy[i]

                if (ny < 0 || ny >= R || nx < 0 || nx >= C || map[ny][nx] == 1) continue
                val new_cost = cur.sum + calc(cur.dir, i)
                // 가본적이 없거나, 가봤더라도 최소 비용을 갱신할 수 있는 길이어야 함
                if (new_cost > map[ny][nx] && isVisited[i][ny][nx]) continue
                q.offer(Dot(nx, ny, i, new_cost))
                isVisited[i][ny][nx] = true
                map[ny][nx] = new_cost
            }
        }
    }

    fun calc(cur_dir : Int, next_dir : Int) : Int = if (cur_dir == -1 || cur_dir == next_dir) 100 else 600
}
data class Dot(val x : Int, val y : Int, val dir : Int, val sum : Int)
```

