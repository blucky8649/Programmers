package Kakao_Internship_2020.키패드_누르기

import java.lang.StringBuilder
import java.util.*
import kotlin.math.abs

class Solution {
    fun solution(numbers: IntArray, hand: String): String {
        val phone = arrayOf(
            Numpad(1, 3), // 0
            Numpad(0, 0), // 1
            Numpad(1, 0), // 2
            Numpad(2, 0), // 3
            Numpad(0, 1), // 4
            Numpad(1, 1), // 5
            Numpad(2, 1), // 6
            Numpad(0, 2), // 7
            Numpad(1, 2), // 8
            Numpad(2, 2)  // 9
        )
        var left_hand = Numpad(0, 3) // *
        var right_hand = Numpad(2, 3) // #

        val sb = StringBuilder()
        for (num in numbers) {
            if (num == 1 || num == 4 || num == 7) {
                sb.append("L")
                left_hand = phone[num]
            }
            else if (num == 3 || num == 6 || num == 9) {
                sb.append("R")
                right_hand = phone[num]
            }
            else {
                // 만약 입력해야할 숫자가 2, 5, 8, 0 이라면, 각각의 손으로부터의 거리에 의한 처리를 해준다.
                val left_dst = abs(left_hand.x - phone[num].x) + abs(left_hand.y - phone[num].y)
                val right_dst = abs(right_hand.x - phone[num].x) + abs(right_hand.y - phone[num].y)
                // 왼손이 더 가까움
                if (left_dst < right_dst) {
                    left_hand = phone[num]
                    sb.append("L")
                }
                // 오른손이 더 가까움
                else if (right_dst < left_dst) {
                    right_hand = phone[num]
                    sb.append("R")
                }
                // 둘의 거리가 같음 : 오른손잡이면 오른손으로, 왼손잡이면 왼손으로 처리
                else {
                    if (hand == "right") {
                        right_hand = phone[num]
                        sb.append("R")
                    } else {
                        left_hand = phone[num]
                        sb.append("L")
                    }
                }

            }
        }
        return sb.toString()
    }
}
data class Numpad(val x : Int, val y : Int)