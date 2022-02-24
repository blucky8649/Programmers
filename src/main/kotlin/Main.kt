
fun main(args : Array<String>) {
    val sol = Kakao_Blind_Recrouitment_2022.양과_늑대.Solution()
    var info= intArrayOf(0,0,1,1,1,0,1,0,1,0,1,1)
    var edges = arrayOf(
        intArrayOf(0, 1),
        intArrayOf(1, 2),
        intArrayOf(1, 4),
        intArrayOf(0, 8),
        intArrayOf(8, 7),
        intArrayOf(9, 10),
        intArrayOf(9, 11),
        intArrayOf(4, 3),
        intArrayOf(6, 5),
        intArrayOf(4, 6),
        intArrayOf(8, 9)
    )

    //println(sol.solution(info, edges))
    info = intArrayOf(0,1,0,1,1,0,1,0,0,1,0)
    edges = arrayOf(
        intArrayOf(0, 1),
        intArrayOf(0, 2),
        intArrayOf(1, 3),
        intArrayOf(1, 4),
        intArrayOf(2, 5),
        intArrayOf(2, 6),
        intArrayOf(3, 7),
        intArrayOf(4, 8),
        intArrayOf(6, 9),
        intArrayOf(9, 10),

    )
    println(sol.solution(info, edges))
}