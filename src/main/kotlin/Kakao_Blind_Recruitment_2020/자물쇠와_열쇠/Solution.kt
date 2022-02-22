package Kakao_Blind_Recruitment_2020.자물쇠와_열쇠

class Solution {
    fun solution(key: Array<IntArray>, lock: Array<IntArray>): Boolean {
        var offset = key.size - 1 // 자물쇠의 시작위치를 나타낸다.
        var keyClone = key // key는 val로 선언된 변수라 수정이 안되기 때문에 keyClone으로 복사

        for (i in 0 until lock.size * 2 - 1) {
            for (j in 0 until lock.size * 2 - 1) {
                // 4번의 회전
                repeat(4) {
                    // 모든 경우를 탐색할 배열 생성
                    val arr = Array(58) { IntArray(58) }
                    for (r in lock.indices) {
                        for (c in lock.indices) {
                            arr[r + offset][c + offset] = lock[r][c]
                        }
                    }
                    // 현재 탐색범위에 키를 대본다.
                    for (y in i until i + key.size) {
                        for (x in j until j + key.size) {
                            arr[y][x] += keyClone[y - i][x - j]
                        }
                    }
                    // 열쇠가 맞는지 체크해본다. (단, 열쇠와 자물쇠의 돌기가 겹치면 안됨)
                    if (check(arr, lock, offset)) {
                        return true
                    }
                    // 열쇠를 돌린다.
                    keyClone = rotateKey(keyClone)
                }
            }
        }

        return false
    }

    fun check(arr: Array<IntArray>, lock: Array<IntArray>, offset: Int): Boolean {
        for (i in lock.indices) {
            for (j in lock.indices) {
                if (arr[i + offset][j + offset] == 0 || arr[i + offset][j + offset] == 2) {
                    return false
                }
            }
        }
        return true
    }

    fun rotateKey(key: Array<IntArray>): Array<IntArray> {
        val rot = Array(key.size) { IntArray(key.size) }

        for (i in key.indices) {
            for (j in key.indices) {
                rot[i][j] = key[key.size - 1 - j][i]
            }
        }

        return rot
    }
}