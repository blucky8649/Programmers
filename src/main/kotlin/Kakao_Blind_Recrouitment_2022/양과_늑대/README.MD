## 문제 설명
2진 트리 모양 초원의 각 노드에 늑대와 양이 한 마리씩 놓여 있습니다. 이 초원의 루트 노드에서 출발하여 각 노드를 돌아다니며 양을 모으려 합니다. 각 노드를 방문할 때 마다 해당 노드에 있던 양과 늑대가 당신을 따라오게 됩니다. 이때, 늑대는 양을 잡아먹을 기회를 노리고 있으며, 당신이 모은 양의 수보다 늑대의 수가 같거나 더 많아지면 바로 모든 양을 잡아먹어 버립니다. 당신은 중간에 양이 늑대에게 잡아먹히지 않도록 하면서 최대한 많은 수의 양을 모아서 다시 루트 노드로 돌아오려 합니다.

![](https://images.velog.io/images/blucky8649/post/dd1d0157-48b2-4b36-8756-239037be516f/image.png)

예를 들어, 위 그림의 경우(루트 노드에는 항상 양이 있습니다) 0번 노드(루트 노드)에서 출발하면 양을 한마리 모을 수 있습니다. 다음으로 1번 노드로 이동하면 당신이 모은 양은 두 마리가 됩니다. 이때, 바로 4번 노드로 이동하면 늑대 한 마리가 당신을 따라오게 됩니다. 아직은 양 2마리, 늑대 1마리로 양이 잡아먹히지 않지만, 이후에 갈 수 있는 아직 방문하지 않은 모든 노드(2, 3, 6, 8번)에는 늑대가 있습니다. 이어서 늑대가 있는 노드로 이동한다면(예를 들어 바로 6번 노드로 이동한다면) 양 2마리, 늑대 2마리가 되어 양이 모두 잡아먹힙니다. 여기서는 0번, 1번 노드를 방문하여 양을 2마리 모은 후, 8번 노드로 이동한 후(양 2마리 늑대 1마리) 이어서 7번, 9번 노드를 방문하면 양 4마리 늑대 1마리가 됩니다. 이제 4번, 6번 노드로 이동하면 양 4마리, 늑대 3마리가 되며, 이제 5번 노드로 이동할 수 있게 됩니다. 따라서 양을 최대 5마리 모을 수 있습니다.

각 노드에 있는 양 또는 늑대에 대한 정보가 담긴 배열 `info`, 2진 트리의 각 노드들의 연결 관계를 담은 2차원 배열 `edges`가 매개변수로 주어질 때, 문제에 제시된 조건에 따라 각 노드를 방문하면서 모을 수 있는 양은 최대 몇 마리인지 return 하도록 solution 함수를 완성해주세요.

## 제한사항
* 2 ≤ `info`의 길이 ≤ 17
* `info`의 원소는 0 또는 1 입니다.
* info[i]는 i번 노드에 있는 양 또는 늑대를 나타냅니다.
* 0은 양, 1은 늑대를 의미합니다.
* info[0]의 값은 항상 0입니다. 즉, 0번 노드(루트 노드)에는 항상 양이 있습니다.
* `edges`의 세로(행) 길이 = `info`의 길이 - 1
* `edges`의 가로(열) 길이 = 2
* `edges`의 각 행은 [부모 노드 번호, 자식 노드 번호] 형태로, 서로 연결된 두 노드를 나타냅니다.
* 동일한 간선에 대한 정보가 중복해서 주어지지 않습니다.
* 항상 하나의 이진 트리 형태로 입력이 주어지며, 잘못된 데이터가 주어지는 경우는 없습니다.
* 0번 노드는 항상 루트 노드입니다.

## 풀이
마땅한 풀이가 생각이 안나 카카오 해설을 보고 이해했습니다.
[카카오 해설 보기](https://tech.kakao.com/2022/01/14/2022-kakao-recruitment-round-1/)

`edges`를 이용하여 인접리스트를 만들고, dfs를 이용하여 문제를 해결하시면 됩니다.

### DFS
백트래킹을 이용하여 가볼 수 있는 모든 경우를 시뮬레이션 하는 로직으로 작성하였습니다. 다음에 가야할 노드는 반복문으로 추가 제거가 용이아게 작성하기 위해 `Queue`를 사용하였습니다. 다음은 의사코드로 작성해본 DFS로직입니다.
```
함수 DFS(현재 노드, 양의 수, 늑대의 수, 다음 노드 리스트) {
	1. 현재 양, 늑대의 수 갱신
    2. 만약 (양 <= 늑대라면)
    	현재 노드는 갈 수 없는 노드이므로 종료
    3. 그렇지 않다면
    	양의 최대값 갱신
	
    4. 다음 노드 리스트에 현재 노드의 자식노드들을 추가
    
    반복문 시작 (다음 노드 리스트의 사이즈 만큼)
    	ㄱ. 방문하려는 노드는 리스트에서 없애준다.
        ㄴ. 재귀 호출 DFS(다음노드, 갱신된 양, 갱신된 늑대, 다음노드 리스트)
        ㄷ. 다시 리스트에 아까 방문한 노드를 추가시키고 다음 노드를 방문할 준비를 한다
    반복문 종료
}
```
위 의사코드는 실제 코드에서 주석으로 활용될 수 있습니다.

## Source Code
```kotlin
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
```

