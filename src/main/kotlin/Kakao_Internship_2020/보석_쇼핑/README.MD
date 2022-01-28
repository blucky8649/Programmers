![프그](https://media.vlpt.us/images/blucky8649/post/01e2164d-9721-41ff-90bb-37b915a8e28d/%EC%8D%B8%EB%84%A4%EC%9D%B4%EB%A3%A8_%EB%B3%B5%EC%82%AC%EB%B3%B8-001%20(3).png)
[문제 풀러 가기!](https://programmers.co.kr/learn/courses/30/lessons/67258)

## 풀이
`투포인터 알고리즘`의 기본 원리만 아신다면 쉽게 풀 수 있는 문제였습니다.

이 문제는 정확성과 함께 효율성도 평가되는 문제기 때문에 O(N^2)로 풀게된다면 효율성에서 매운맛을 보실 수 있습니다.
따라서 **O(N)**으로 문제를 해결해야 하는데 그 해결책은 **투포인터** 알고리즘입니다.

투포인터 알고리즘은 한 루프 안에 두 개의 포인트(start, end) 포인트를 두고 특정 범위 안에 있는 수의 합을 구하는 알고리즘입니다.

따라서 위 알고리즘으로 gems 범위를 벗어날 때 까지 end 포인터를 이동시키며 데이터를 추가하고, 만약 중간에 모든 보석종류가 모였으면 start 포인터를 이동시키며 데이터를 삭제하면서 가장 최적의 범위값을 찾아내면 됩니다. 

따라서 보석이 몇개 포함되어있는지 세어보기 위해 Set과, Map을 사용하여 체크하였습니다.

```kotlin
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
            
           // 최소 범위 갱신
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
```

