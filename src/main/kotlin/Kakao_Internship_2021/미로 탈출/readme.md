[문제 풀러 가기!](https://programmers.co.kr/learn/courses/30/lessons/81304)

## 풀이

기본 다익스트라 개념에 더해 함정의 상태에 따른 처리를 해주어야 합니다.
그렇기 때문에 기존 다익스트라에서 `isVisited` 배열을 2차원 배열로 구성하여 함정 상태 정보도 같이 체크해 주어야합니다.

우선순위 큐를 꺼내보며 현재 노드의 함정과 다음 노드의 **함정 발동 여부를 체크**하여, 둘의 발동 여부가 같다고 하면 함정은 두번 발동되는 것이 되므로 원래의 경로로 이동하고, 만약 그렇지 않다면 원래 경로의 반대방향으로 이동하여야 합니다.

### 1. 인접 행렬 구성
다익스트라 문제를 풀기위한 인접행렬을 구성합니다. 여기서 주의할 점이 있습니다.
>서로 다른 두 방 사이에 직접 연결된 길이 **여러 개 존재**할 수도 있습니다.

위 조건대로, 같은 경로가 여러개 존재할 수 있기 때문에, 여러 경로중에 제일 짧은 경로를 선택하여야 합니다.
```kotlin
for (road in roads) {
    val (start, end, weight) = road
    if (matrix[start][end] > weight) {
        matrix[start][end] = weight
    }
}
```

### 2. 다익스트라 실행
다익스트라로 최단거리를 탐색합니다. 앞서 언급했듯 조건이 상당히 까다롭습니다. 우선 순위 큐에 현재 노드, 가중치, 상태 값을 넘겨 주어 각각의 노드 상황에 따른 상태를 분석하여야합니다. 예를 들어, traps 배열이 [2, 3]일때, 상태가 '01'이면 2번 함정은 켜져있고, 3번 함정은 꺼져있는 상태가 됩니다. 이런식으로 상태값을 비트연산하여 유지해주시면 됩니다.
```kotlin
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

                // 현재 밟고 있는 노드가 함정이라면, 함정의 상태를 바꿔주어야 함.
                if (trap == curNode.node) {
                    state = state and bit.inv()
                } else {
                    map[trap] = true
                }
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
```
