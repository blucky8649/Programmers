
## 문제 보기
전무로 승진한 라이언은 기분이 너무 좋아 프렌즈를 이끌고 특별 휴가를 가기로 했다.
내친김에 여행 계획까지 구상하던 라이언은 재미있는 게임을 생각해냈고 역시 전무로 승진할만한 인재라고 스스로에게 감탄했다.

라이언이 구상한(그리고 아마도 라이언만 즐거울만한) 게임은, 카카오 프렌즈를 두 팀으로 나누고, 각 팀이 같은 곳을 다른 순서로 방문하도록 해서 먼저 순회를 마친 팀이 승리하는 것이다.

그냥 지도를 주고 게임을 시작하면 재미가 덜해지므로, 라이언은 방문할 곳의 2차원 좌표 값을 구하고 각 장소를 이진트리의 노드가 되도록 구성한 후, 순회 방법을 힌트로 주어 각 팀이 스스로 경로를 찾도록 할 계획이다.

라이언은 아래와 같은 특별한 규칙으로 트리 노드들을 구성한다.

* 트리를 구성하는 모든 노드의 x, y 좌표 값은 정수이다.
모든 노드는 서로 다른 x값을 가진다.
* 같은 레벨(level)에 있는 노드는 같은 y 좌표를 가진다.
* 자식 노드의 y 값은 항상 부모 노드보다 작다.
* 임의의 노드 V의 왼쪽 서브 트리(left subtree)에 있는 모든 노드의 x값은 V의 x값보다 작다.
* 임의의 노드 V의 오른쪽 서브 트리(right subtree)에 있는 모든 노드의 x값은 V의 x값보다 크다.
아래 예시를 확인해보자.

라이언의 규칙에 맞게 이진트리의 노드만 좌표 평면에 그리면 다음과 같다. (이진트리의 각 노드에는 1부터 N까지 순서대로 번호가 붙어있다.)

![](https://images.velog.io/images/blucky8649/post/4a9d3a92-b967-4c93-b612-dde63eaadaf2/image.png)

이제, 노드를 잇는 간선(edge)을 모두 그리면 아래와 같은 모양이 된다.

![](https://images.velog.io/images/blucky8649/post/4f3531da-8fe5-4bc0-a7e1-f1dd5c166883/image.png)

위 이진트리에서 전위 순회(preorder), 후위 순회(postorder)를 한 결과는 다음과 같고, 이것은 각 팀이 방문해야 할 순서를 의미한다.

전위 순회 : 7, 4, 6, 9, 1, 8, 5, 2, 3
후위 순회 : 9, 6, 5, 8, 1, 4, 3, 2, 7
다행히 두 팀 모두 머리를 모아 분석한 끝에 라이언의 의도를 간신히 알아차렸다.

그러나 여전히 문제는 남아있다. 노드의 수가 예시처럼 적다면 쉽게 해결할 수 있겠지만, 예상대로 라이언은 그렇게 할 생각이 전혀 없었다.

이제 당신이 나설 때가 되었다.

곤경에 빠진 카카오 프렌즈를 위해 이진트리를 구성하는 노드들의 좌표가 담긴 배열 nodeinfo가 매개변수로 주어질 때,
노드들로 구성된 이진트리를 전위 순회, 후위 순회한 결과를 2차원 배열에 순서대로 담아 return 하도록 solution 함수를 완성하자.

  ### 제한 사항
  * nodeinfo는 이진트리를 구성하는 각 노드의 좌표가 1번 노드부터 순서대로 들어있는 2차원 배열이다.
* nodeinfo의 길이는 `1` 이상 `10,000` 이하이다.
* nodeinfo[i] 는 i + 1번 노드의 좌표이며, [x축 좌표, y축 좌표] 순으로 들어있다.
* 모든 노드의 좌표 값은 `0` 이상 `100,000` 이하인 정수이다.
* 트리의 깊이가 `1,000` 이하인 경우만 입력으로 주어진다.
* 모든 노드의 좌표는 문제에 주어진 규칙을 따르며, 잘못된 노드 위치가 주어지는 경우는 없다.

## 풀이
트리 순회 방법만 알면 풀 수 있는 문제입니다.

### Data Class 지정
트리를 순회하기 위해서는 현재 노드, 좌노드, 우노드의 정보가 필요합니다. 따라서 저는 다음과 같이 `Node`와 `Tree` 데이터 클래스를 사용하였습니다.

```kotlin
data class Tree(var left: Node, var right: Node)
data class Node(val x: Int, val y: Int, val num: Int): Comparable<Node> {
    override fun compareTo(other: Node): Int {
        if (y == other.y) {
            return other.x - x
        }
        return other.y - y
    }
}
```

주어진 문제에서 노드의 위치를 x, y축으로 정했기 때문에 `Node`의 매개변수로 xy축, 그리고 노드 번호를 입력받을 수 있도록 작성하였습니다.

또한 문제에서 주어진 우선순위에 맞게 Comparable을 활용하여 정렬 기준을 재조정하였습니다.

### 입력값 처리
주어지는 입력값을 앞서 정의했던 `Tree`클래스로 이루어진 배열을 사용합니다. 이 배열의 인덱스값은 해당 노드의 번호가 되겠네요.

```kotlin
trees = Array(pq.size + 1) { Tree(emptyNode, emptyNode) }
```

### 각 노드의 좌노드, 우노드를 정해준다.
문제에서 언급된 노드의 조건을 보면 다음과 같이 적혀있습니다.  
> * 임의의 노드 V의 왼쪽 서브 트리(left subtree)에 있는 모든 노드의 x값은 V의 x값보다 작다.
> * 임의의 노드 V의 오른쪽 서브 트리(right subtree)에 있는 모든 노드의 x값은 V의 x값보다 크다.

이 조건을 보는 순간 루트노드부터 재귀적으로 x값을 비교하여 좌노드인지 우노드인지 구분하여야겠다고 생각했습니다.

만약 구하고자 하는 x축의 값이 루트노드의 x축보다 작은데 루트노드의 좌노드는 이미 다른 노드가 자리잡고 있다면 그 다른 노드의 자식노드는 비었는지 확인해봐야합니다.

다음 코드는 위와같은 방식을 재귀로 적용한 코드입니다. 하위노드를 계속 파고들어 적절한 위치를 잡는거죠.
```kotlin
private fun selectNode(n: Node, cur: Node) {
    // 만약 구하고자 하는 n노드의 x값이 cur노드의 x축보다 작으면 왼쪽 서브노드로 들어가게 된다.
    if (n.x < cur.x) {
        // 그러나 cur 노드의 왼쪽 서브노드에 이미 다른 노드가 있다면 그 다음노드의 하위노드가 비었는지 탐색한다.
        if (Solution.trees[cur.num].left != Solution.emptyNode) {
            selectNode(n, Solution.trees[cur.num].left)
        } else {
            // cur의 왼쪽이 비어있다면 왼쪽 노드에 값을 저장하고 return
            Solution.trees[cur.num].left = Node(n.x, n.y, n.num)
            return
        }
    }
    // 오른쪽 서브노드일 경우
    else {
        if (Solution.trees[cur.num].right != Solution.emptyNode) {
            selectNode(n, Solution.trees[cur.num].right)
        } else {
            // cur의 오른쪽이 비어있다면 오른쪽 노드에 값을 저장하고 return
            Solution.trees[cur.num].right = Node(n.x, n.y, n.num)
            return
        }
    }
}
```
### 트리 순회
이제 트리 순회만 하시면 끝이납니다.
`전위 순회`는 근 -> 좌 -> 우,  
`중위 순회`는 좌 -> 근 -> 우,  
`후외 순회`는 좌 -> 우 -> 근으로 탐색합니다.

다음은 전위 순회를 나타내는 코드입니다.
```kotlin
private fun makePreorder(num: Int, root: Tree) {
	// 현재 노드를 먼저 출력합니다.
    sb.append("$num").append(" ")
    // 왼쪽 노드가 존재한다면, 왼쪽 서브트리에 대해 전위 순회를 진행합니다.
    if (root.left != emptyNode) {
        makePreorder(root.left.num, Solution.trees[root.left.num])
    }
    // 오른쪽 노드가 존재한다면, 오른쪽 서브트리에 대해 전위 순회를 진행합니다.
    if (root.right != emptyNode) {
        makePreorder(root.right.num, Solution.trees[root.right.num])
    }
}
```

그러면 후위 순회는 어떻게 나타낼까요? 좌노드를 먼저 순회한 다음, 근 노드를 출력하고, 후에 우노드를 순회하면 됩니다.
```kotlin
private fun makePostorder(num: Int, root: Tree) {
    if (root.left != Solution.emptyNode) {
        makePostorder(root.left.num, Solution.trees[root.left.num])
    }
    if (root.right != Solution.emptyNode) {
        makePostorder(root.right.num, Solution.trees[root.right.num])
    }
    Solution.sb.append("$num").append(" ")
}
```

## Source Code
```kotlin
package Kakao_Blind_Recruitment_2019.길_찾기_게임

import java.util.*

const val EMPTY = -1

class Solution {
    companion object {
        lateinit var trees: Array<Tree>
        var sb = StringBuilder()

        val emptyNode = Node(EMPTY, EMPTY, EMPTY)
    }

    fun solution(nodeinfo: Array<IntArray>): Array<IntArray> {
        var answer = Array(2) { IntArray(nodeinfo.size) }
        val pq = PriorityQueue<Node>()
        for (i in nodeinfo.indices) {
            val x = nodeinfo[i][0]
            val y = nodeinfo[i][1]
            pq.offer(Node(x, y, i + 1))
        }
        trees = Array(pq.size + 1) { Tree(emptyNode, emptyNode) }
        val root = pq.poll()

        while (pq.isNotEmpty()) {
            val n = pq.poll()
            selectNode(n, root)
        }
        makePreorder(root.num, trees[root.num])
        answer[0] = sb.substring(0, sb.length - 1).split(" ").map { it.toInt() }.toIntArray()
        sb = StringBuilder()
        makePostorder(root.num, trees[root.num])
        answer[1] = sb.substring(0, sb.length - 1).split(" ").map { it.toInt() }.toIntArray()

        return answer
    }

    private fun makePreorder(num: Int, root: Tree) {
        sb.append("$num").append(" ")
        if (root.left != emptyNode) {
            makePreorder(root.left.num, trees[root.left.num])
        }
        if (root.right != emptyNode) {
            makePreorder(root.right.num, trees[root.right.num])
        }
    }

    private fun makePostorder(num: Int, root: Tree) {
        if (root.left != emptyNode) {
            makePostorder(root.left.num, trees[root.left.num])
        }
        if (root.right != emptyNode) {
            makePostorder(root.right.num, trees[root.right.num])
        }
        sb.append("$num").append(" ")
    }

    private fun selectNode(n: Node, cur: Node) {
        // 만약 구하고자 하는 n노드의 x값이 cur노드의 x축보다 작으면 왼쪽 서브노드로 들어가게 된다.
        if (n.x < cur.x) {
            // 그러나 cur 노드의 왼쪽 서브노드에 이미 다른 노드가 있다면 그 다음노드의 하위노드가 비었는지 탐색한다.
            if (trees[cur.num].left != emptyNode) {
                selectNode(n, trees[cur.num].left)
            } else {
                // cur의 왼쪽이 비어있다면 왼쪽 노드에 값을 저장하고 return
                trees[cur.num].left = Node(n.x, n.y, n.num)
                return
            }
        }
        // 오른쪽 서브노드일 경우
        else {
            if (trees[cur.num].right != emptyNode) {
                selectNode(n, trees[cur.num].right)
            } else {
                // cur의 왼쪽이 비어있다면 왼쪽 노드에 값을 저장하고 return
                trees[cur.num].right = Node(n.x, n.y, n.num)
                return
            }
        }
    }
}

data class Tree(var left: Node, var right: Node)
data class Node(val x: Int, val y: Int, val num: Int) : Comparable<Node> {
    override fun compareTo(other: Node): Int {
        if (y == other.y) {
            return other.x - x
        }
        return other.y - y
    }
}

```
