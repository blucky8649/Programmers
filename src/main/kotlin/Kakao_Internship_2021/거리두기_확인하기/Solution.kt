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