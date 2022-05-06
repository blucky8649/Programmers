[문제 풀러 가기!](https://programmers.co.kr/learn/courses/30/lessons/64063)

## 풀이
효율성 테스트가 있는 문제입니다. 효율성 있다고 겁먹지 마시고, 정확도만 잘 맞춘다 해도 실제 시험에서 부분 점수로 인정되니 자신있게 풀어보시기 바랍니다.

이 문제의 풀이 프로세스는 다음과 같습니다.
>1. 손님의 원하는 방을 순차적으로 배정해준다.
>2. 만약, 손님이 원하는 방이 가득 차면 원하는 방 번호보다 크면서 비어있는 방 중 첫 번째 방을 배정해줍니다.

여기서 2번을 얼마나 효율적으로 풀어냈는가에 따라 효율성에서 점수가 달라집니다.

여기서 정확성만을 빠르게 풀고 넘어가시는 것도 하나의 전략이라고 말씀드리고 싶습니다.
그냥 **순차적으로 빈방을 탐색**해주시면 됩니다.

그러나 k의 범위가 10의 12승까지이므로 효율성을 생각하신다면 `해시맵`을 사용하셔서 **현재 방: 다음 방** 을 갱신해주시면서 푸시면 됩니다.

```kotlin
package Kakao_Internship_2019.호텔방배정

class Solution {
    val map = HashMap<Long, Long>() // 현재 번호 : 다음 방 번호
    fun solution(k: Long, room_number: LongArray): LongArray {
        var answer = LongArray(room_number.size)
        for (i in room_number.indices) {
            // 빈 방을 찾아서 예약한다.
            answer[i] = findRoom(room_number[i])
        }

        return answer
    }
    fun findRoom(room: Long): Long {
        if (!map.containsKey(room)) {
            // 만약 현재 방이 비었다면 그 방으로 배정한다.
            map[room] = room + 1
            return room
        }

        // 비어있지 않다면, 다음 방들을 탐색하여 찾아야 한다.
        val next = map[room]
        val empty = findRoom(next!!)
        map[room] = empty // 빈 방을 찾고 다음 방 번호를 갱신해준다.

        return empty
    }
}
```
