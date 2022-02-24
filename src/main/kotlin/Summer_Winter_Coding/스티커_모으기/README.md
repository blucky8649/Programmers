## 문제 설명
N개의 스티커가 원형으로 연결되어 있습니다. 다음 그림은 N = 8인 경우의 예시입니다.  
![](https://images.velog.io/images/blucky8649/post/711008a1-b8aa-4eb2-b686-94c16f1216eb/image.png)  
원형으로 연결된 스티커에서 몇 장의 스티커를 뜯어내어 뜯어낸 스티커에 적힌 숫자의 합이 최대가 되도록 하고 싶습니다. 단 스티커 한 장을 뜯어내면 양쪽으로 인접해있는 스티커는 찢어져서 사용할 수 없게 됩니다.

예를 들어 위 그림에서 14가 적힌 스티커를 뜯으면 인접해있는 10, 6이 적힌 스티커는 사용할 수 없습니다. 스티커에 적힌 숫자가 배열 형태로 주어질 때, 스티커를 뜯어내어 얻을 수 있는 숫자의 합의 최댓값을 return 하는 solution 함수를 완성해 주세요. 원형의 스티커 모양을 위해 배열의 첫 번째 원소와 마지막 원소가 서로 연결되어 있다고 간주합니다.

## 제한 사항
>sticker는 원형으로 연결된 스티커의 각 칸에 적힌 숫자가 순서대로 들어있는 배열로, 길이(N)는 1 이상 100,000 이하입니다.
>sticker의 각 원소는 스티커의 각 칸에 적힌 숫자이며, 각 칸에 적힌 숫자는 1 이상 100 이하의 자연수입니다.
>원형의 스티커 모양을 위해 sticker 배열의 첫 번째 원소와 마지막 원소가 서로 연결되어있다고 간주합니다.

## 풀이
DP문제입니다.

완전탐색으로 풀어도 되려나 싶었지만, N의 최대범위가 10만이기 때문에 DP를 시도해야겠다고 생각했습니다.

DP문제는 점화식을 세우면 90%는 푸신겁니다.

문제에서 스티커는 원형이고, 하나를 뜯으면 양 옆의 스티커가 훼손된다고 적혀있습니다.

따라서, 스티커는 원형이기 때문에, **첫 번재 스티커를 뜯게된다면 마지막 스티커를 못쓰게 됩니다.**

따라서 **첫 번째 스티커를 뜯는경우와, 두 번째 스티커를 뜯는 경우**를 나눠서 DP로 계산해보았습니다.


### 점화식
점화식은 **경우에 따라 0에서 i번째 까지의 스티커를 뽑는 최대값입니다.**
`D1`: 첫 번째 스티커를 뜯은 경우
`D2`: 두 번째 스티커를 뜯은 경우

다음 코드와 같이 i번째 스티커를 뜯는 경우와, 뜯지 않는 경우의 최대값을 비교해주시면 됩니다.
```java
dp[D1][i] = Math.max(dp[D1][i - 1], sticker[i] + dp[D1][i - 2]);
dp[D2][i] = Math.max(dp[D2][i - 1], sticker[i] + dp[D2][i - 2]);
```

## Source Code
```java
import java.util.Arrays;

class Solution {
    final int D1 = 0; // 첫 번째 스티커를 뜯었을 경우
    final int D2 = 1; // 두 번째 스티커를 뜯었을 경우
    public int solution(int sticker[]) {
        int answer = 0;
        int m = sticker.length;
        
        if (m == 1) {
            return sticker[0];
        }
        if (m == 2) {
            return Math.max(sticker[0], sticker[1]);
        }
        
        int[][] dp = new int[2][m];
        // D1: 첫 번째 스티커를 뜯었을 경우, 두번째 스티커는 뜯지 못한다.
        dp[D1][0] = sticker[0];
        dp[D1][1] = sticker[0];
        
        // D2: 두 번째 스티커를 뜯었을 경우, 첫 번째 스티커는 뜯지 못한다.
        dp[D2][0] = 0;
        dp[D2][1] = sticker[1];
        
        
        for (int i = 2 ; i < m ; i++) {
            dp[D1][i] = Math.max(dp[D1][i - 1], sticker[i] + dp[D1][i - 2]);
            dp[D2][i] = Math.max(dp[D2][i - 1], sticker[i] + dp[D2][i - 2]);
        }
        // 첫 번째 스티커를 뜯은 경우, 마지막 스티커는 뜯지 못한다.
        dp[D1][m - 1] = dp[D1][m - 2];
        
        
        
        return Math.max(dp[D1][m - 1], dp[D2][m - 1]);
    }
}

```

```kotlin
package Summer_Winter_Coding.스티커_모으기
import kotlin.math.max

const val D1 = 0 // 첫 번째 스티커를 뜯었을 경우
const val D2 = 0 // 두 번째 스티커를 뜯었을 경우
class Solution {
    fun solution(sticker: IntArray): Int {
        val m = sticker.size
        if (m == 1) {
            return sticker[0]
        }
        if (m == 2) {
            return max(sticker[0], sticker[1])
        }
        val dp = Array(2) { IntArray(m) }

        // D1: 첫 번째 스티커를 뜯었을 경우, 두번째 스티커는 뜯지 못한다.
        dp[D1][0] = sticker[0]
        dp[D1][1] = sticker[0]

        // D2: 두 번째 스티커를 뜯었을 경우, 첫 번째 스티커는 뜯지 못한다.
        dp[D2][0] = 0
        dp[D2][1] = sticker[1]
        for (i in 2 until m) {
            dp[D1][i] = max(dp[D1][i - 1], sticker[i] + dp[D1][i - 2])
            dp[D2][i] = max(dp[D2][i - 1], sticker[i] + dp[D2][i - 2])
        }
        // 첫 번째 스티커를 뜯은 경우, 마지막 스티커는 뜯지 못한다.
        dp[D1][m - 1] = dp[D1][m - 2]
        return max(dp[D1][m - 1], dp[D1][m - 1])
    }
}
```
