package Kakao_Blind_Recruitment_2021.카드_짝_맞추기

import kotlin.math.*
import java.util.*

const val INF = 1_000_000_000
class Solution {

    companion object {
        lateinit var Board: Array<IntArray>
        val dr = arrayOf(-1, 0, 1, 0)
        val dc = arrayOf(0, 1, 0, -1)
    }
    fun permutation(src: Point): Int {
        var res = INF
        for (idxCard in 1..6) {
            val card = ArrayList<Point>()

            for (i in Board.indices) {
                for (j in Board.indices) {
                    if (Board[i][j] == idxCard) {
                        card.add(Point(i, j, 0))
                    }
                }
            }

            if (card.isEmpty()) continue

            val op1 = BFS(src, card[0]) + BFS(card[0], card[1]) + 2
            val op2 = BFS(src, card[1]) + BFS(card[1], card[0]) + 2

            for (c in card) {
                Board[c.row][c.col] = 0
            }

            res = min(res, op1 + permutation(card[1]))
            res = min(res, op2 + permutation(card[0]))

            for (c in card) {
                Board[c.row][c.col] = idxCard
            }
        }

        return if (res == INF) 0 else res
    }
    fun BFS(start: Point, end: Point): Int {
        val q: Queue<Point> = LinkedList<Point>()
        val isVisited = Array(4) { BooleanArray(4) }
        q.offer(start)

        while (q.isNotEmpty()) {
            val cur = q.poll()

            if (cur.row == end.row && cur.col == end.col) {
                return cur.cnt
            }

            for (i in dr.indices) {
                var nr = cur.row + dr[i]
                var nc = cur.col + dc[i]

                if (!inRange(nr, nc)) continue

                if (!isVisited[nr][nc]) {
                    isVisited[nr][nc] = true
                    q.offer(Point(nr, nc, cur.cnt + 1))
                }

                repeat(3) {
                    if (!inRange(nr + dr[i], nc + dc[i])) return@repeat
                    if (Board[nr][nc] != 0) return@repeat

                    nr += dr[i]
                    nc += dc[i]
                }
                if (!isVisited[nr][nc]) {
                    isVisited[nr][nc] = true
                    q.offer(Point(nr, nc, cur.cnt + 1))
                }
            }
        }
        return INF
    }
    fun inRange(row: Int, col: Int) = (row in 0..3 && col in 0..3)
    fun solution(board: Array<IntArray>, r: Int, c: Int): Int {
        Board = board
        val src = Point(r, c, 0)
        return permutation(src)
    }
}
data class Point(val row: Int, val col: Int, val cnt: Int)