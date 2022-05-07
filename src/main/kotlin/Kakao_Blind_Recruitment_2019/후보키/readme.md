[문제 풀러 가기!](https://programmers.co.kr/learn/courses/30/lessons/42890)

## 풀이
레벨 2임에도 푸는데 꽤나 고전했던 문제였습니다.  
어쩌면 비트 다루는데 능숙지 않은 저의 문제겠지만요.  

이 문제를 풀려면 최소성과 유일성에 대한 개념을 숙지하셔야 합니다. 
>유일성(uniqueness) : 릴레이션에 있는 모든 튜플에 대해 유일하게 식별되어야 한다.  
>최소성(minimality) : 유일성을 가진 키를 구성하는 속성(Attribute) 중 하나라도 제외하는 경우 유일성이 깨지는 것을 의미한다. 즉, 릴레이션의 모든 튜플을 유일하게 식별하는 데 꼭 필요한 속성들로만 구성되어야 한다.

그렇기에 테이블에서 **최소성, 유일성을 만족할 수 있는 모든 경우의 수**를 구하면 됩니다.
![](https://velog.velcdn.com/images/blucky8649/post/06097309-37ce-4a01-aab4-5850e8d797a5/image.png)

위 테이블에서 어떤 속성이 후보키가 될 수 있을까요?  
학번, 이름+전공 테이블입니다.  

전공 + 학년은 키가 중복이 되므로 유일성을 만족하지 않습니다.  

그러나 6번째 행의 학년을 2가 아닌 1로 바꾸면 어떻게될까요?  
그렇습니다. 유일성과 최소성을 모두 만족시키는 후보키가 될 수 있습니다.  
그렇게 되면 총 후보키는 **학번, 이름+전공, 전공+학년**이 될 수 있습니다.  

이제 차근 차근 풀어볼까요?

### 1. 기본 로직
비트를 사용하여 `0`부터 `1<<tableSize` 만큼의 반복문을 사용하여 해결했습니다. 만약 테이블의 가로 사이즈가 4라면, 이진법상으로 0000부터 1111까지 반복문을 수행하게 되는것입니다.

예를 들어 첫 번째 + 두번째 열의 유일성과 최소성을 검사하고 싶다면 0011번째에서 검사하면 됩니다.

```kotlin
for (i in 1 until (1 shl n)) {
    if (!checkUnique(i)) continue
    if (!checkMinimum(minList, i)) continue
    minList.add(i)
    answer++
}
```

### 2. 유일성 검사
특정 속성 조합이 유일성을 만족하는지 확인합니다.
비트정보를 컬럼의 인덱스로 변환시켜서 두 컬럼에 해당하는 값들을 concat 해주시면 됩니다.
```kotlin
fun checkUnique(index: Int) : Boolean {
    // 1의 비트가 켜진 곳들이 유일성을 만족하는지 판별
    val list = ArrayList<Int>()
    for (i in 0 until n) {
        val bit = 1 shl i // 0001 0010 0100 1000
        if (index and bit != 0) {
            list.add(i)
        }
    }
    val uniqueSet = HashSet<String>()

    table.forEach { tuple ->
        val sb = StringBuilder()
        list.forEach { index ->
            sb.append(tuple[index]).append("?")
        }
        uniqueSet.add(sb.toString())
    }
    if (uniqueSet.size < table.size) {
        return false
    }
    return true
}
```

### 3. 최소성 검사
최소성을 검사하는지 확인합니다. 만약 0001 비트가 최소성과 유일성을 만족시키는 후보키로 선정이 되었다면, 0011은 최소성에 부합하지 않는 키가 됩니다.

처리 방법은 and연산을 해주시면 됩니다. 이미 0001이 후보키로 들어와 있기 때문에 0001 and 0011 == 0001 인지 확인해주면 되는 것이죠.

```kotlin
fun checkMinimum(list: ArrayList<Int>, index: Int) : Boolean {
    list.forEach { key ->
        if (key and index == key) {
            return false
        }
    }
    return true
}
```

## Source Code
```kotlin
package Kakao_Blind_Recruitment_2019.후보키

class Solution {
    var n = 0
    lateinit var table: Array<Array<String>>
    val minList = ArrayList<Int>()
    fun solution(relation: Array<Array<String>>): Int {
        var answer = 0
        table = relation
        n = relation[0].size
        // 비트를 이용하여 유일성을 만족하는지 식별 0 -> 1 shl relation.size 만큼 반복문을 돈다.
        for (i in 1 until (1 shl n)) {
            if (!checkUnique(i)) continue
            if (!checkMinimum(minList, i)) continue
            minList.add(i)
            answer++
        }
        return answer
    }
    fun checkMinimum(list: ArrayList<Int>, index: Int) : Boolean {
        list.forEach { key ->
            if (key and index == key) {
                return false
            }
        }
        return true
    }
    fun checkUnique(index: Int) : Boolean {
        // 1의 비트가 켜진 곳들이 유일성을 만족하는지 판별
        val list = ArrayList<Int>()
        for (i in 0 until n) {
            val bit = 1 shl i // 0001 0010 0100 1000
            if (index and bit != 0) {
                list.add(i)
            }
        }
        val uniqueSet = HashSet<String>()

        table.forEach { tuple ->
            val sb = StringBuilder()
            list.forEach { index ->
                sb.append(tuple[index]).append("?")
            }
            uniqueSet.add(sb.toString())
        }
        if (uniqueSet.size < table.size) {
            return false
        }
        return true
    }
}

```

설명이 쉽지 않네요;;^^
