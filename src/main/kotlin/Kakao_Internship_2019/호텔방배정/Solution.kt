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