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