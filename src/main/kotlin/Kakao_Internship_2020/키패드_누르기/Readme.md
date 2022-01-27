[![szz](https://media.vlpt.us/images/blucky8649/post/96d95821-f607-43ef-bd80-ff667297ed4e/%EC%8D%B8%EB%84%A4%EC%9D%B4%EB%A3%A8_%EB%B3%B5%EC%82%AC%EB%B3%B8-001%20(2).png)](https://programmers.co.kr/learn/courses/30/lessons/67256)
[문제 풀러 가기!](https://programmers.co.kr/learn/courses/30/lessons/67256)

## 풀이
2차원 좌표계에서의 `맨해튼 거리` 구하는 공식만 알면 풀 수 있는 문제입니다.

### data class 선언

```kotlin
data class Numpad(val x : Int, val y : Int)
```
x축과 y축의 정보를 받는 data class를 선언해줍니다.

### Keypad 배열 초기화
```kotlin
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
```
배열의 인덱스는 **`누르려고 하는 숫자의 번호`**고 해당 인덱스 안의 값은 **`누르려고 하는 숫자의 위치`**입니다.

### 키 누르기
```kotlin
var left_hand = Numpad(0, 3) // *
var right_hand = Numpad(2, 3) // #

val sb = StringBuilder()
// 입력값으로 받은 numbers 배열을 하나씩 돌리면서 예외처리를 해봅시다.
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
```

## Source code
### Kotlin
```kotlin
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
```

### Java
```java
class Solution {
    public String solution(int[] numbers, String hand) {
        hand[] button = new hand[13];
        button[1] = new hand(1, 1);
        button[2] = new hand(2, 1);
        button[3] = new hand(3, 1);
        button[4] = new hand(1, 2);
        button[5] = new hand(2, 2);
        button[6] = new hand(3, 2);
        button[7] = new hand(1, 3);
        button[8] = new hand(2, 3);
        button[9] = new hand(3, 3);
        button[0] = new hand(2, 4);
        button[11] = new hand(1, 4);
        button[12] = new hand(3, 4);
        
        hand left = button[11];
        hand right = button[12];
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < numbers.length ; i++){
            switch(numbers[i]){
                //왼짝
                case 1 : 
                case 4 : 
                case 7 : sb.append("L"); left = button[numbers[i]] ; break;
                //오른짝
                case 3 :
                case 6 :
                case 9 : sb.append("R"); right = button[numbers[i]]; break;
                //가운뎃짝
                case 2 :
                case 5 :
                case 8 :
                case 0 : 
                    int left_dst = Math.abs(left.x - button[numbers[i]].x) + Math.abs(left.y - button[numbers[i]].y);
                    int right_dst = Math.abs(right.x - button[numbers[i]].x) + Math.abs(right.y - button[numbers[i]].y);
                    
                    
                    if(left_dst < right_dst){ 
                        left = button[numbers[i]];
                        sb.append("L");
                    }else if(left_dst > right_dst){
                        right = button[numbers[i]];
                        sb.append("R");
                    }else{                     
                        if(hand.equals("right")){ 
                            right = button[numbers[i]];
                            sb.append("R");
                        }else{                      
                            left = button[numbers[i]]; 
                            sb.append("L");
                        }
                    }
                        break;
            }
        }
        
        String answer = sb.toString();
        return answer;
    }
}
class hand{
    int x;
    int y;
    hand(int x, int y){
        this.x=x;
        this.y=y;
    }
}
```
