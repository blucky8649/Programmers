[문제 풀러 가기!](https://programmers.co.kr/learn/courses/30/lessons/81305)

## 풀이
매개변수 탐색 + DFS를 이용하면 풀 수 있는 문제입니다.

이 문제를 풀기 전에 이분 탐색의 lower bound, upper bound에 대해 숙지하고 접근하셔야 됩니다.

### 이분 탐색
문제에서 요구하는 조건은 다음과 같습니다.
>가장 큰 그룹의 인원을 최소화시켜야 합니다.

그렇기 때문에 매개변수 탐색 중에 lower bound를 사용했습니다.

lower bound와 upper bound의 차이는 다음 사진을 보시면 됩니다.
![](https://velog.velcdn.com/images/blucky8649/post/93cac835-611d-471a-8434-ecdf2d5ecbb4/image.png)
```kotlin
var low = Collections.max(num.toList())
var high = 10000 * 10000

while (low < high) {
    val mid = (low + high) / 2
    // k 값보다 적 다는 것 : 인원(limit)을 줄여서 더 많은 그룹을 만들어야 함
    if (countGroup(num, mid) <= k) {
        high = mid
    }
    // k 보다 작거나 같을 경우, 같을 경우에도 인원을 최대한 줄여야 함
    else {
        low = mid + 1
    }

}
return low
```

### DFS 탐색
매개변수 탐색으로 구한 limit값을 기반으로 dfs를 수행하여 총 끊어낸 그룹을 세어주어야 합니다.
```kotlin
fun countGroup(num: IntArray, limit: Int) : Int {
    count = 0
    dfs(root, limit, num) // 총 몇 회 끊어냈는지 세어줌
    return count + 1
}
fun dfs(cur: Int, limit: Int, num: IntArray) : Int{
    var left = 0
    if (tree[cur].left != -1) {
        left = dfs(tree[cur].left, limit, num)
    }
    var right = 0
    if (tree[cur].right != -1) {
        right = dfs(tree[cur].right, limit, num)
    }

    // 1. 양쪽 자식들의 총 합과 나 자신이 limit를 넘지 않는다.
    if (num[cur] + left + right <= limit) {
        return num[cur] + left + right
    }
    // 2. 양쪽 자식들중에서 한 자식의 합만 가져가야 할 때 
    // 그룹의 인원을 최소화하기 위해 더 작은 자식을 데려간다.
    if (num[cur] + min(left, right) <= limit) {
        count ++ // 한 자식을 끊어낸다.
        return num[cur] + min(left, right)
    }

    // 3. 양쪽 자식 다 데려가지 못하고 다 끊어내야 할 때
    count += 2 // 두 자식을 끊어낸다.
    return num[cur]
}
```

## Source Code
```kotlin
package Kakao_Internship_2021.시험장_나누기

import java.util.*
import kotlin.math.min

class Solution {
    // 1. 매개변수 탐색으로 Mid 값만큼의 인원 제한을 둔다면 몇묶음이 나오는지 계산해야한다.
    lateinit var parent: IntArray
    lateinit var tree: Array<Node>
    var count = 0
    var root = -1
    fun solution(k: Int, num: IntArray, links: Array<IntArray>): Int {
        parent = IntArray(num.size) { -1 }
        tree = Array(num.size) { Node(-1, -1) }
        links.forEachIndexed { i, link ->
            val left = link[0]
            val right = link[1]
            tree[i] = Node(left, right)
            if (left != - 1) {
                parent[left] = i
            }
            if (right != - 1) {
                parent[right] = i
            }
        }
        parent.forEachIndexed { i,  p ->
            if (p == -1) {
                root = i
                return@forEachIndexed
            }
        }

        var low = Collections.max(num.toList())
        var high = 10000 * 10000

        while (low < high) {
            val mid = (low + high) / 2
            // k 값보다 적 다는 것 : 인원(limit)을 줄여서 더 많은 그룹을 만들어야 함
            if (countGroup(num, mid) <= k) {
                high = mid
            }
            // k 보다 작거나 같을 경우, 같을 경우에도 인원을 최대한 줄여야 함
            else {
                low = mid + 1
            }

        }
        return low
    }
    fun countGroup(num: IntArray, limit: Int) : Int {
        count = 0
        dfs(root, limit, num) // 총 몇 회 끊어냈는지 세어줌
        return count + 1
    }
    fun dfs(cur: Int, limit: Int, num: IntArray) : Int{
        var left = 0
        if (tree[cur].left != -1) {
            left = dfs(tree[cur].left, limit, num)
        }
        var right = 0
        if (tree[cur].right != -1) {
            right = dfs(tree[cur].right, limit, num)
        }

        // 1. 양쪽 자식들의 총 합과 나 자신이 limit를 넘지 않는다.
        if (num[cur] + left + right <= limit) {
            return num[cur] + left + right
        }
        // 2. 양쪽 자식들중에서 한 자식의 합만 가져가야 할 때 그룹의 인원을 최소화하기 위해 더 작은 자식을 데려간다.
        if (num[cur] + min(left, right) <= limit) {
            count ++ // 한 자식을 끊어낸다.
            return num[cur] + min(left, right)
        }

        // 3. 양쪽 자식 다 데려가지 못하고 다 끊어내야 할 때
        count += 2 // 두 자식을 끊어낸다.
        return num[cur]
    }
}

data class Node(val left: Int, val right: Int)

```
