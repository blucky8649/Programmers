package Kakao_Internship_2020.보석_쇼핑

class Solution {
    fun solution(gems: Array<String>): IntArray {
        var answer = IntArray(2)
        // 보석 종류의 개수 구하기 Using 'SET'
        val set = HashSet<String>()

        for (gem in gems) {
            set.add(gem)
        }
        val map = HashMap<String, Int>()

        var dst = Integer.MAX_VALUE
        var start = 0 ; var end = 0

        while (true) {
            // 만약 모든 종류의 보석이 포함 되어있다면 start 값을 올려 범위를 최소로 만든다.
            if (set.size == map.size) {
                map[gems[start]] = map[gems[start]]?.minus(1) ?: 0

                if (map[gems[start]] == 0) {
                    map.remove(gems[start])
                }

                start++
            }
            // end 값이 gems 배열의 범위를 벗어났다면 반복문 종료
            else if (end == gems.size) {
                break
            }
            // 만약 모든 종류의 보석이 포함되지 않았다면 end값을 증가시켜 보석을 추가한다.
            else {
                map[gems[end]] = map.getOrDefault(gems[end], 0) + 1
                end++
            }

            if (set.size == map.size) {
                if (end - start < dst) {
                    dst = end - start
                    answer[0] = start + 1
                    answer[1] = end
                }
            }
        }
        return answer
    }
}