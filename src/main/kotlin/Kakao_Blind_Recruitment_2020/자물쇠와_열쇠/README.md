[문제 풀러 가기!](https://programmers.co.kr/learn/courses/30/lessons/60059)

## 문제 설명
고고학자인 "튜브"는 고대 유적지에서 보물과 유적이 가득할 것으로 추정되는 비밀의 문을 발견하였습니다. 그런데 문을 열려고 살펴보니 특이한 형태의 자물쇠로 잠겨 있었고 문 앞에는 특이한 형태의 열쇠와 함께 자물쇠를 푸는 방법에 대해 다음과 같이 설명해 주는 종이가 발견되었습니다.

잠겨있는 자물쇠는 격자 한 칸의 크기가 1 x 1인 N x N 크기의 정사각 격자 형태이고 특이한 모양의 열쇠는 M x M 크기인 정사각 격자 형태로 되어 있습니다.

자물쇠에는 홈이 파여 있고 열쇠 또한 홈과 돌기 부분이 있습니다. 열쇠는 회전과 이동이 가능하며 열쇠의 돌기 부분을 자물쇠의 홈 부분에 딱 맞게 채우면 자물쇠가 열리게 되는 구조입니다. 자물쇠 영역을 벗어난 부분에 있는 열쇠의 홈과 돌기는 자물쇠를 여는 데 영향을 주지 않지만, 자물쇠 영역 내에서는 열쇠의 돌기 부분과 자물쇠의 홈 부분이 정확히 일치해야 하며 열쇠의 돌기와 자물쇠의 돌기가 만나서는 안됩니다. 또한 자물쇠의 모든 홈을 채워 비어있는 곳이 없어야 자물쇠를 열 수 있습니다.

열쇠를 나타내는 2차원 배열 key와 자물쇠를 나타내는 2차원 배열 lock이 매개변수로 주어질 때, 열쇠로 자물쇠를 열수 있으면 true를, 열 수 없으면 false를 return 하도록 solution 함수를 완성해주세요.

## 제한 사항
* key는 M x M(3 ≤ M ≤ 20, M은 자연수)크기 2차원 배열입니다.
* lock은 N x N(3 ≤ N ≤ 20, N은 자연수)크기 2차원 배열입니다.
* M은 항상 N 이하입니다.
* key와 lock의 원소는 0 또는 1로 이루어져 있습니다.
* 0은 홈 부분, 1은 돌기 부분을 나타냅니다.


## 풀이
열쇠를 계속 돌려가면서 모든 경우를 탐색해야하는 문제입니다.
전에 카카오는 완전탐색만 공부하면 된다는 글을 본 적이 있는데, 요즘 기출 문제를 풀면서 체감하고 있습니다.

열쇠가 자물쇠 칸을 일부 벗어나는 경우도 체크해주어야 하기 때문에 다음 사진과 같이 배열을 만들고, 가운데에 자물쇠를 넣어야합니다.
#### ![](https://images.velog.io/images/blucky8649/post/1cd8859b-0206-42f7-9900-dbe5218d0d3a/image.png)

`자물쇠`: 검정 진한 테두리
`열쇠`: 빨간 테두리

위 그림과 같이 자물쇠를 가운데에 두고 모든 경우에 열쇠를 대보시면 됩니다.
효율성 제한이 없는 문제는 다소 과감하고 Brute-Force하게 풀어도 될듯 싶네요.

## Source Code
```kotlin
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
```
