[문제 풀러 가기!](https://programmers.co.kr/learn/courses/30/lessons/72415)

## 풀이

난이도가 있는 문제라 깊이 이해하려고 자바로도 풀어보았습니다.
필드의 범위가 4 * 4, 카드의 종류가 6장 밖에 되지 않으므로 `완전탐색`을 시도해볼 수 있습니다.

만약 카드가 3장 있다고 하면, (1, 2, 3), (2, 1, 3), (3, 2, 1)... 등등 모든 경우를 재귀적으로 탐색해주면 됩니다.

### 백트래킹을 이용한 완전탐색
`백트래킹`을 이용하여 첫 번째 카드 짝을 없애고, 다른 경우의 수를 재귀적으로 탐색해봐야 합니다. 탐색을 위해 시뮬레이션 후 필드를 원상복귀 시키는 것이 포인트입니다.

```kotlin
// src : 현재 커서의 위치
int permutation(Point src) {
    int res = INF;
    // 카드를 고르는 모든 경우를 다 따져 주어야 함
    for (int idxCard = 1 ; idxCard <= 6 ; idxCard++) {
        ArrayList<Point> card = new ArrayList<>();
        for (int i = 0 ; i < 4 ; i++) {
          	for (int j = 0 ; j < 4 ; j++) {

          	if (Board[i][j] == idxCard) {
            	card.add(new Point(i, j, 0));
          	}
    	}
    }

        if (card.isEmpty()) continue;

        int op1 = BFS(src, card.get(0)) + BFS(card.get(0), card.get(1)) + 2;
        int op2 = BFS(src, card.get(1)) + BFS(card.get(1), card.get(0)) + 2;

        // 카드 짝을 맞췄다면, 카드를 제거해주어야 한다.
        for (Point c: card)
        	Board[c.row][c.col] = 0;

		// 한 짝의 카드 두개 중에, 어떤 카드를 먼저 뒤집을지에 따라 최소거리가 바뀐다.
        res = Math.min(res, op1 + permutation(card.get(1)));
        res = Math.min(res, op2 + permutation(card.get(0)));

        // 다른 경우도 탐색해 보기 위해 필드 원상복귀
        for (Point c: card)
        	Board[c.row][c.col] = idxCard;

    }
    // res가 INF라는 것은 더 이상 뽑을 카드가 없다는 뜻이므로 0을 리턴한다.
    if (res == INF) return 0;
    return res;
}
```

### BFS를 사용하여 start -> end 로 가는 최소 키 조작 수를 반환한다.

이번 문제는 한번에 필드 끝 혹은 다음 카드로 가는 Ctrl버튼이 있기 때문에, 그 경우도 고려해주면서 작성하셔야 합니다.
```kotlin
int BFS(Point start, Point end) {
    Queue<Point> q = new LinkedList<>();
    boolean[][] isVisited = new boolean[4][4];
    q.offer(start);

    while (!q.isEmpty()) {
        Point cur = q.poll();

        if (cur.row == end.row && cur.col == end.col) {
            return cur.cnt;
        }

        for (int i = 0 ; i < 4 ; i++) {
            int nr = cur.row + dr[i];
            int nc = cur.col + dc[i];
            if (!inRange(nr, nc)) continue;

            if (!isVisited[nr][nc]) {
                isVisited[nr][nc] = true;
                q.offer(new Point(nr, nc, cur.cnt + 1));
            }

            // Ctrl키를 눌러 한 번에 끝까지 가는 경우도 고려
            for (int c = 0 ; c < 3 ; c++) {
            if (!inRange(nr + dr[i], nc + dc[i])) break;
            if (Board[nr][nc] != 0) break;

            nr += dr[i];
            nc += dc[i];
        }

            if (!isVisited[nr][nc]) {
                isVisited[nr][nc] = true;
                q.offer(new Point(nr, nc, cur.cnt + 1));
            }

        }
    }
    return INF;
}
```

## Source Code
### Java
```java
import java.util.*;

class Solution {
    final int INF = 1_000_000_000;
    int[][] Board;
    int[] dr = {-1, 0, 1, 0};
    int[] dc = {0, 1, 0, -1};
    
    int permutation(Point src) {
        int res = INF;
        // 카드를 고르는 모든 경우를 다 따져 주어야 함
        for (int idxCard = 1 ; idxCard <= 6 ; idxCard++) {
            ArrayList<Point> card = new ArrayList<>();
            for (int i = 0 ; i < 4 ; i++) {
                for (int j = 0 ; j < 4 ; j++) {
                    
                    if (Board[i][j] == idxCard) {
                        card.add(new Point(i, j, 0));
                    }
                }
            }
            
            if (card.isEmpty()) continue;
            
            int op1 = BFS(src, card.get(0)) + BFS(card.get(0), card.get(1)) + 2;
            int op2 = BFS(src, card.get(1)) + BFS(card.get(1), card.get(0)) + 2;

            // 카드 짝을 맞췄다면, 카드를 제거해주어야 한다.
            for (Point c: card) 
                Board[c.row][c.col] = 0;
            
            
            res = Math.min(res, op1 + permutation(card.get(1)));
            res = Math.min(res, op2 + permutation(card.get(0)));
            
            // 다른 경우도 탐색해 보기 위해 필드 원상복귀
            for (Point c: card) 
                Board[c.row][c.col] = idxCard;
            
        }
        // res가 INF라는 것은 더 이상 뽑을 카드가 없다는 뜻이므로 0을 리턴한다.
        if (res == INF) return 0;
        return res;
    }
    // start에서 end까지 가는 최소 키 조작 횟수를 리턴
    int BFS(Point start, Point end) {
        Queue<Point> q = new LinkedList<>();
        boolean[][] isVisited = new boolean[4][4];
        q.offer(start);
        
        while (!q.isEmpty()) {
            Point cur = q.poll();
            
            if (cur.row == end.row && cur.col == end.col) {
                return cur.cnt;
            }
            
            for (int i = 0 ; i < 4 ; i++) {
                int nr = cur.row + dr[i];
                int nc = cur.col + dc[i];
                if (!inRange(nr, nc)) continue;
                
                if (!isVisited[nr][nc]) {
                    isVisited[nr][nc] = true;
                    q.offer(new Point(nr, nc, cur.cnt + 1));
                }
                
                // Ctrl키를 눌러 한 번에 끝까지 가는 경우도 고려
                for (int c = 0 ; c < 3 ; c++) {
                    if (!inRange(nr + dr[i], nc + dc[i])) break;
                    if (Board[nr][nc] != 0) break;
                    
                    nr += dr[i];
                    nc += dc[i];
                }
                
                if (!isVisited[nr][nc]) {
                    isVisited[nr][nc] = true;
                    q.offer(new Point(nr, nc, cur.cnt + 1));
                }
                
            }
        }
        return INF;
    }
    boolean inRange(int r, int c) {
        return (r >= 0 && r < 4 && c >= 0 && c < 4);
    }
    public int solution(int[][] board, int r, int c) {
        Board = board;
        Point src = new Point(r, c, 0);
        return permutation(src);
    }
    
}
class Point {
    int row, col, cnt;
    Point(int r, int c, int t) {
        row = r;
        col = c;
        cnt = t;
    }
}

```

### Kotlin
```kotlin
import kotlin.math.*
import java.util.*

const val INF = 1_000_000_000
class Solution {
    
    companion object {
        lateinit var Board: Array<IntArray>
        val dr = arrayOf(-1, 0, 1, 0)
        val dc = arrayOf(0, 1, 0, -1)
    }
    fun permutation(src: Point): Int {
        var res = INF
        for (idxCard in 1..6) {
            val card = ArrayList<Point>()
            
            for (i in Board.indices) {
                for (j in Board.indices) {
                    if (Board[i][j] == idxCard) {
                        card.add(Point(i, j, 0))
                    }
                }
            }
            
            if (card.isEmpty()) continue
            
            val op1 = BFS(src, card[0]) + BFS(card[0], card[1]) + 2
            val op2 = BFS(src, card[1]) + BFS(card[1], card[0]) + 2
            
            for (c in card) {
                Board[c.row][c.col] = 0
            }
            
            res = min(res, op1 + permutation(card[1]))
            res = min(res, op2 + permutation(card[0]))
            
            for (c in card) {
                Board[c.row][c.col] = idxCard
            }
        }
        
        return if (res == INF) 0 else res
    }
    fun BFS(start: Point, end: Point): Int {
        val q: Queue<Point> = LinkedList<Point>()
        val isVisited = Array(4) { BooleanArray(4) }
        q.offer(start)
        
        while (q.isNotEmpty()) {
            val cur = q.poll()
            
            if (cur.row == end.row && cur.col == end.col) {
                return cur.cnt
            }
            
            for (i in dr.indices) {
                var nr = cur.row + dr[i]
                var nc = cur.col + dc[i]
                
                if (!inRange(nr, nc)) continue
                
                if (!isVisited[nr][nc]) {
                    isVisited[nr][nc] = true
                    q.offer(Point(nr, nc, cur.cnt + 1))
                }
                
                repeat(3) {
                    if (!inRange(nr + dr[i], nc + dc[i])) return@repeat
                    if (Board[nr][nc] != 0) return@repeat
                    
                    nr += dr[i]
                    nc += dc[i]
                }
                if (!isVisited[nr][nc]) {
                    isVisited[nr][nc] = true
                    q.offer(Point(nr, nc, cur.cnt + 1))
                }
            }
        }
        return INF
    }
    fun inRange(row: Int, col: Int) = (row in 0..3 && col in 0..3)
    fun solution(board: Array<IntArray>, r: Int, c: Int): Int {
        Board = board
        val src = Point(r, c, 0)
        return permutation(src)
    }
}
data class Point(val row: Int, val col: Int, val cnt: Int)
```
