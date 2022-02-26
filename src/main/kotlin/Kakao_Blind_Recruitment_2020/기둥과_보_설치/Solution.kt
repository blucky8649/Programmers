package Kakao_Blind_Recruitment_2020.기둥과_보_설치

const val PILLAR = 0
const val PAPER = 1

class Solution {
    private lateinit var map: Array<Array<BooleanArray>>
    fun solution(n: Int, build_frame: Array<IntArray>): Array<IntArray> {

        map = Array(2) { Array(n + 2) { BooleanArray(n + 2) } }

        for (i in build_frame.indices) {
            val c = build_frame[i][0]
            val r = build_frame[i][1]
            val type = build_frame[i][2]
            val inr = build_frame[i][3]

            // 설치할 때
            if (inr == 1) {
                when (type) {
                    PILLAR -> if (isPossible(r, c, PILLAR)) map[PILLAR][r][c] = true
                    PAPER -> if (isPossible(r, c, PAPER)) map[PAPER][r][c] = true
                }
            }
            // 삭제할 때
            else {
                map[type][r][c] = false // 일단 삭제해본다.

                //만약 지울 수 없다면 다시 복원한다.
                if (!isRemovable(r, c, type)) {
                    map[type][r][c] = true
                }
            }
        }
        // 전체 맵을 탐색하면서, 기둥과 보의 정보를 담는다.
        val infoArrayList = ArrayList<Info>()
        for (type in map.indices) {
            for (r in map[0].indices) {
                for (c in map[0][0].indices) {
                    if (!map[type][r][c]) continue
                    infoArrayList.add(Info(c, r, type))
                }
            }
        }
        // 우선순위에 맞게 정렬해준다.
        infoArrayList.sort()

        // 정답이 될 배열에 정렬된 정보를 담는다.
        var answer = Array(infoArrayList.size) { IntArray(3) }
        for (i in infoArrayList.indices) {
            answer[i][0] = infoArrayList[i].c
            answer[i][1] = infoArrayList[i].r
            answer[i][2] = infoArrayList[i].type
        }
        return answer
    }
    fun isRemovable(r: Int, c: Int, type: Int): Boolean {
        when (type) {
            PILLAR -> {
                // 위에 있는 기둥이 규칙을 준수 하는지
                if (map[PILLAR][r + 1][c] && !isPossible(r + 1, c, PILLAR)) {
                    return false
                }
                // 이 기둥에 받쳐지고 있던 왼쪽 보가 규칙을 준수 하는지
                if (c > 0 && map[PAPER][r + 1][c - 1] && !isPossible(r + 1, c - 1, PAPER)) {
                    return false
                }

                // 이 기둥에 받쳐지고 있던 오른쪽 보가 규칙을 준수 하는지
                if (map[PAPER][r + 1][c] && !isPossible(r + 1, c, PAPER)) {
                    return false
                }
            }
            PAPER -> {
                // 현재 보의 왼족 편 위에 올라가있는 기둥은 괜찮은지
                if (map[PILLAR][r][c] && !isPossible(r, c, PILLAR)) {
                    return false
                }
                // 현재 보의 오른쪽 편 위에 올라가 있는 기둥은 괜찮은지
                if (map[PILLAR][r][c + 1] && !isPossible(r, c + 1, PILLAR)) {
                    return false
                }
                // 현재 보를 삭제함으로써 왼쪽에 붙어있던 보는 괜찮은지
                if (c > 0 && map[PAPER][r][c - 1] && !isPossible(r, c - 1, PAPER)) {
                    return false
                }
                // 현재 보를 삭제함으로써 오른쪽에 붙어있던 보는 괜찮은지
                if (map[PAPER][r][c + 1] && !isPossible(r, c + 1, PAPER)) {
                    return false
                }
            }
        }
        return true
    }
    fun isPossible(r: Int, c: Int, type: Int): Boolean {
        when (type) {
            PILLAR -> {
                // 바닥에 있을 경우
                if (r == 0) return true
                // 왼쪽에서 보가 받치고 있을 경우
                if (c > 0 && map[PAPER][r][c - 1]) return true
                // 현재 위치에 보가 설치되어 있는 경우
                if (map[PAPER][r][c]) return true
                // 밑에 또 다른 기둥이 설치되어 있는 경우
                if (map[PILLAR][r - 1][c]) return true
            }

            PAPER -> {
                // 밑에서 기둥이 받치고 있는 경우
                if (map[PILLAR][r - 1][c]) return true
                // 보의 오른쪽밑에 기둥이 받치고 있는 경우
                if (map[PILLAR][r - 1][c + 1]) return true
                // 양 옆에 보가 있는 경우
                if (c > 0 && (map[PAPER][r][c - 1]) && map[PAPER][r][c + 1]) return true
            }
        }
        return false
    }
}
data class Info(val c: Int, val r: Int, val type: Int) : Comparable<Info> {
    override fun compareTo(o: Info): Int {
        if (c == o.c) {
            if (r == o.r) {
                return type - o.type
            }
            return r - o.r
        }
        return c - o.c
    }
}

