## 문제 설명
레스토랑을 운영하고 있는 "스카피"는 레스토랑 내부가 너무 낡아 친구들과 함께 직접 리모델링 하기로 했습니다. 레스토랑이 있는 곳은 스노우타운으로 매우 추운 지역이어서 내부 공사를 하는 도중에 주기적으로 외벽의 상태를 점검해야 할 필요가 있습니다.

레스토랑의 구조는 완전히 동그란 모양이고 외벽의 총 둘레는 n미터이며, 외벽의 몇몇 지점은 추위가 심할 경우 손상될 수도 있는 취약한 지점들이 있습니다. 따라서 내부 공사 도중에도 외벽의 취약 지점들이 손상되지 않았는 지, 주기적으로 친구들을 보내서 점검을 하기로 했습니다. 다만, 빠른 공사 진행을 위해 점검 시간을 1시간으로 제한했습니다. 친구들이 1시간 동안 이동할 수 있는 거리는 제각각이기 때문에, 최소한의 친구들을 투입해 취약 지점을 점검하고 나머지 친구들은 내부 공사를 돕도록 하려고 합니다. 편의 상 레스토랑의 정북 방향 지점을 0으로 나타내며, 취약 지점의 위치는 정북 방향 지점으로부터 시계 방향으로 떨어진 거리로 나타냅니다. 또, 친구들은 출발 지점부터 시계, 혹은 반시계 방향으로 외벽을 따라서만 이동합니다.

외벽의 길이 n, 취약 지점의 위치가 담긴 배열 weak, 각 친구가 1시간 동안 이동할 수 있는 거리가 담긴 배열 dist가 매개변수로 주어질 때, 취약 지점을 점검하기 위해 보내야 하는 친구 수의 최소값을 return 하도록 solution 함수를 완성해주세요.

## 제한 사항
> * n은 1 이상 200 이하인 자연수입니다.
> * weak의 길이는 1 이상 15 이하입니다.
> * 서로 다른 두 취약점의 위치가 같은 경우는 주어지지 않습니다.
> * 취약 지점의 위치는 **오름차순**으로 정렬되어 주어집니다.
> * weak의 원소는 0 이상 n - 1 이하인 정수입니다.
> * dist의 길이는 1 이상 8 이하입니다.
> * dist의 원소는 1 이상 100 이하인 자연수입니다.
> * 친구들을 모두 투입해도 **취약 지점을 전부 점검할 수 없는 경우에는 -1을 return** 해주세요.

## 풀이
비트마스킹에 익숙해지고 싶다면 [후보키](https://programmers.co.kr/learn/courses/30/lessons/42890) 문제를 풀어보시면 좋을 것 같습니다.

재귀를 이용한 완전탐색으로 풀었습니다. 아래 사진처럼 파란색 임의의 시작점에서 출발하여 모든 지점을 가보면서 비트 마스킹을 이용하여 갈 수 있는곳의 비트를 올려줍니다. 



![](https://images.velog.io/images/blucky8649/post/2d2149f5-75a7-445f-a4cb-5f6b4c88a4a6/image.png)

먄약 지점 10에서 출발하는 인력의 이동거리가 총 4라면, 현재 위치에서 커버할 수 있는 지점은 10지점과 1지점 밖에 없습니다. 따라서 총 4군데 지점에서 두 군데의 비트를 켜게 된다면 `0011`이 되는 것이죠.

그러고 만약 한 사람이 모든 구간을 커버한다면 (비트는 1111이 되겠죠) 최소값을 1로 바꾸고 리턴하면 됩니다.

만약 한 사람으로도 다 채워지지 못한다면, 추가적인 다른 인력을 투입하여(재귀사용) 비트를 1111로 만드는것이 핵심입니다.

비트는 다음과 같이 or 연산을 사용하면 켤 수 있습니다.
```kotlin
visited = visited or (1 shl nextPos)
```

전체 소스코드도 주석으로도 꼼꼼히 정리해놨으니 참고해주시면 도움될 것 같네요.

## Source Code
```kotlin
import kotlin.math.*

class Solution {
    companion object {
        lateinit var Weak: IntArray
        lateinit var dst: IntArray
        var answer = Int.MAX_VALUE
        var N = 0
    }
    fun solution(n: Int, weak: IntArray, dist: IntArray): Int {
        N = n
        Weak = weak
        dst = dist
        // 오름차순 정렬
        dst.sort()
        
        
        // i는 시작지점을 나타낸다.
        for (i in Weak.indices) {
            solve(1, i, 0)
        }
        return if (answer == Int.MAX_VALUE) -1 else answer
    }
    fun solve(depth: Int, pos: Int, isVisited: Int) {
        // 이미 전체 인원이 투입되었다면 종료한다.
        if (depth > dst.size) return
        if (answer <= depth) return
        
        var visited = isVisited
        
        // 1. pos위치에서 dist[dist.size - depth]만큼 간다면 얼마만큼 갈 수 있을 지 작성
        for (i in Weak.indices) {
            // pos 에서 nextposition 만큼 갈 수 있는지 탐색
            val nextPos = (pos + i) % Weak.size
            
            // 현재 포지션과, 다음 포지션 사이의 거리를 구한다.
            var diff = Weak[nextPos] - Weak[pos];
            
            // 만약 다음 포지션이 weak의 최대 인덱스를 지나고 다시 돌아왔다면 
            if (diff < 0) {
                // diff에서 n만큼을 더 더해주면 된다.
                // ex) n이 12일때, 10지점에서 1지점으로 가려면 -9 + 12 = 3 만큼의 거리가 발생한다.
                diff += N
            }
            
            // 가장 긴 거리를 가는 사람부터 탐색을 보낸다.
            // nextPos 까지 커버를 못한다면 종료한다.
            if (diff > dst[dst.size - depth]) break
            
            
            // 그렇지 않다면 해당 포지션의 방문 비트를 켠다.
            visited = visited or (1 shl nextPos)
            
        }
        // 만약 모든 지점을 탐색하였으면 최소값을 갱신한다.
        
        if (visited == (1 shl Weak.size) - 1) {
            answer = min(answer, depth)
            return
        }
        
        // 모든 지역을 커버하지 못했다면, 다른 사람을 추가적으로 보내야한다.
        for (i in Weak.indices) {
            // 만약 이미 방문한 곳이라면 넘긴다.
            
            if (visited and (1 shl i) != 0) continue
            solve(depth + 1, i, visited)
        }
    }
}
```
