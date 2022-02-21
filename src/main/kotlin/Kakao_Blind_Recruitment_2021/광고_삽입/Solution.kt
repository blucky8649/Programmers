package Kakao_Blind_Recruitment_2021.광고_삽입

import java.util.*

class Solution {
    fun solution(play_time: String, adv_time: String, logs: Array<String>): String {
        val pt = convertToSeconds(play_time) // 플레이 타임 초 환산
        val at = convertToSeconds(adv_time) // 광고 타임 초 환산
        val totalSec = IntArray(360_000) // 100시간의 데이터를 기록

        for (i in logs.indices) {
            val range = logs[i].split("-")
            val st = convertToSeconds(range[0]) // 시작 시간 초 환산
            val et = convertToSeconds(range[1]) // 종료 시간 초 환산

            for (k in st until et) {
                totalSec[k]++
            }
        }
        var curTime: Long = 0 // 현재 구간의 시간
        for (i in 0 .. at) {
            curTime += totalSec[i]
        }
        var stOfMaxTime = 0 // 최대 구간의 시작 점을 반환함
        var maxTime: Long = curTime // 구간 시간 중 가장 큰 시간대
        for (i in at + 1 .. pt) {
            curTime = curTime + totalSec[i] - totalSec[i - at]
            if (curTime > maxTime) {
                maxTime  = curTime
                stOfMaxTime = i - at + 1
            }
        }

        return secondToString(stOfMaxTime)
    }
    fun convertToSeconds(time: String): Int {
        val (hour, min, sec) = time.split(":").map { it.toInt() }
        return (hour * 3600) + (min * 60) + sec
    }
    fun secondToString(time: Int): String = String.format("%02d:%02d:%02d", time / 3600, time / 60 % 60, time % 60)
}