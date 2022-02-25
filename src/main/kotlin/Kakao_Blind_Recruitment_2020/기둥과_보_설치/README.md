## 문제 설명
빙하가 깨지면서 스노우타운에 떠내려 온 "죠르디"는 인생 2막을 위해 주택 건축사업에 뛰어들기로 결심하였습니다. "죠르디"는 기둥과 보를 이용하여 벽면 구조물을 자동으로 세우는 로봇을 개발할 계획인데, 그에 앞서 로봇의 동작을 시뮬레이션 할 수 있는 프로그램을 만들고 있습니다.
프로그램은 2차원 가상 벽면에 기둥과 보를 이용한 구조물을 설치할 수 있는데, 기둥과 보는 길이가 1인 선분으로 표현되며 다음과 같은 규칙을 가지고 있습니다.

>기둥은 바닥 위에 있거나 보의 한쪽 끝 부분 위에 있거나, 또는 다른 기둥 위에 있어야 합니다.
>보는 한쪽 끝 부분이 기둥 위에 있거나, 또는 양쪽 끝 부분이 다른 보와 동시에 연결되어 있어야 합니다.
단, 바닥은 벽면의 맨 아래 지면을 말합니다.

2차원 벽면은 `n x n` 크기 정사각 격자 형태이며, 각 격자는 `1 x 1` 크기입니다. 맨 처음 벽면은 비어있는 상태입니다. 기둥과 보는 격자선의 교차점에 걸치지 않고, 격자 칸의 각 변에 정확히 일치하도록 설치할 수 있습니다. 다음은 기둥과 보를 설치해 구조물을 만든 예시입니다.

![](https://images.velog.io/images/blucky8649/post/3c3b3ceb-444f-427d-9979-1fab70ec4a91/image.png)

예를 들어, 위 그림은 다음 순서에 따라 구조물을 만들었습니다.

(1, 0)에서 위쪽으로 기둥을 하나 설치 후, (1, 1)에서 오른쪽으로 보를 하나 만듭니다.
(2, 1)에서 위쪽으로 기둥을 하나 설치 후, (2, 2)에서 오른쪽으로 보를 하나 만듭니다.
(5, 0)에서 위쪽으로 기둥을 하나 설치 후, (5, 1)에서 위쪽으로 기둥을 하나 더 설치합니다.
(4, 2)에서 오른쪽으로 보를 설치 후, (3, 2)에서 오른쪽으로 보를 설치합니다.
만약 (4, 2)에서 오른쪽으로 보를 먼저 설치하지 않고, (3, 2)에서 오른쪽으로 보를 설치하려 한다면 2번 규칙에 맞지 않으므로 설치가 되지 않습니다. 기둥과 보를 삭제하는 기능도 있는데 기둥과 보를 삭제한 후에 남은 기둥과 보들 또한 위 규칙을 만족해야 합니다. 만약, 작업을 수행한 결과가 조건을 만족하지 않는다면 해당 작업은 무시됩니다.

벽면의 크기 n, 기둥과 보를 설치하거나 삭제하는 작업이 순서대로 담긴 2차원 배열 build_frame이 매개변수로 주어질 때, 모든 명령어를 수행한 후 구조물의 상태를 return 하도록 solution 함수를 완성해주세요.

## 제한사항
* n은 5 이상 100 이하인 자연수입니다.
* build_frame의 세로(행) 길이는 1 이상 1,000 이하입니다.
* build_frame의 가로(열) 길이는 4입니다.
* build_frame의 원소는 [x, y, a, b]형태입니다.
* x, y는 기둥, 보를 설치 또는 삭제할 교차점의 좌표이며, [가로 좌표, 세로 좌표] 형태입니다.
* a는 설치 또는 삭제할 구조물의 종류를 나타내며, 0은 기둥, 1은 보를 나타냅니다.
* b는 구조물을 설치할 지, 혹은 삭제할 지를 나타내며 0은 삭제, 1은 설치를 나타냅니다.
* 벽면을 벗어나게 기둥, 보를 설치하는 경우는 없습니다.
* 바닥에 보를 설치 하는 경우는 없습니다.
* 구조물은 교차점 좌표를 기준으로 보는 오른쪽, 기둥은 위쪽 방향으로 설치 또는 삭제합니다.
* 구조물이 겹치도록 설치하는 경우와, 없는 구조물을 삭제하는 경우는 입력으로 주어지지 않습니다.
* 최종 구조물의 상태는 아래 규칙에 맞춰 return 해주세요.
* return 하는 배열은 가로(열) 길이가 3인 2차원 배열로, 각 구조물의 좌표를 담고있어야 합니다.
* return 하는 배열의 원소는 [x, y, a] 형식입니다.
* x, y는 기둥, 보의 교차점 좌표이며, [가로 좌표, 세로 좌표] 형태입니다.
* 기둥, 보는 교차점 좌표를 기준으로 오른쪽, 또는 위쪽 방향으로 설치되어 있음을 나타냅니다.
* a는 구조물의 종류를 나타내며, 0은 기둥, 1은 보를 나타냅니다.
* return 하는 배열은 x좌표 기준으로 오름차순 정렬하며, x좌표가 같을 경우 y좌표 기준으로 오름차순 정렬해주세요.
* x, y좌표가 모두 같은 경우 기둥이 보보다 앞에 오면 됩니다.

## 풀이
이 문제는 구현문제로써 기둥과 보의 삽입, 삭제에 대한 예외처리를 꼼꼼하게 해주셔야합니다.
Boolean타입의 3차원 배열을 선언하여 기둥 정보, 보 정보를 담아서 해결하였습니다.

단계별로 나눠서 풀어봅시다.

### 상수 선언
기둥과 보의 직관적인 구분을 위해 다음과 같이 상수로 선언하였습니다.
```kotlin
const val PILLAR = 0 // 기둥
const val PAPER = 1 // 보
```
### data class 선언
데이터의 우선순위는 다음과 같습니다.
> * return 하는 배열은 **x좌표 기준으로 오름차순** 정렬하며, **x좌표가 같을 경우 y좌표 기준으로 오름차순** 정렬해주세요.
> * x, y좌표가 모두 같은 경우 **기둥이 보보다 앞에** 오면 됩니다.

우선순위에 맞도록 정렬하기 위해서 `Comparable` 인터페이스의 `compareTo` 함수를 오버라이딩 해줍니다

`r`: 문제의 y좌표
`c`: 문제의 x좌표
`type`: 기둥 / 보 정보를 나타내는 상수값

```kotlin
data class Info(val c: Int, val r: Int, val type: Int) : Comparable<Info> {
    override fun compareTo(o: Info): Int {
        if (c == o.c) {
            if (r == o.r) {
            	// 기둥이 보보다 앞에 오도록 정렬
                return type - o.type
            }
            // y좌표를 기준으로 오름차순
            return r - o.r
        }
        //x좌표를 기준으로 정렬
        return c - o.c
    }
}
```

### 기둥, 보 정보를 나타내는 배열 선언
앞서 언급했듯, 3차원 배열을 이용하여 선언하였습니다.
```kotlin
map = Array(2) { Array(n + 2) { BooleanArray(n + 2) } }
// map[type][r][c]
```

### build_frame 배열을 순차적으로 탐색하면서 건물을 짓거나 철거한다.
해당 위치에 기둥이나 보를 설치 / 삭제할 수 있을 경우에만 처리합니다.
```kotlin
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
```
### 설치가 가능한지 판별하는 메서드 생성
기둥과 보를 설치하려면 다음과 같은 조건을 항상 만족해야합니다.
> * 기둥은 바닥 위에 있거나 보의 한쪽 끝 부분 위에 있거나, 또는 다른 기둥 위에 있어야 합니다.
>* 보는 한쪽 끝 부분이 기둥 위에 있거나, 또는 양쪽 끝 부분이 다른 보와 동시에 연결되어 있어야 합니다.
단, 바닥은 벽면의 맨 아래 지면을 말합니다.

그렇기 때문에 다음과 같이 기둥과 보의 설치 규칙에 맞는지 판별하는 코드를 작성합니다.
```kotlin
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
```

### 삭제가 가능한지 판별하는 메서드 생성
이번에는 삭제가 가능한지 판별하는 메서드를 작성합니다. 자신이 삭제되므로서 주변 보, 기둥의 **규칙 준수에 영향을 주면 삭제가 불가능**하도록 처리합니다.
```kotlin
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
```
### 지어진 건물의 기둥, 보 정보를 리스트에 담는다.
기둥, 보 정보 배열을 전체 탐색하면서 값이 true일 경우에만 리스트에 저장하도록 구현합니다. 또한, 우선순위에 맞게 정렬해줍니다.

```kotlin
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
// 정답 반환
return answer
```

## Source Code
```kotlin
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
```
