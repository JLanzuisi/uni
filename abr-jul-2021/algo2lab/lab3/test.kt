import kotlin.system.exitProcess

fun main() {
    val start: Long
    val consumedTime: Long
    fun createSeq(type: String, N: Int): Array<Int> {
        var sequence = Array<Int>(N, { 0 })
        // Create random sequence
        if (type == "random") {
            // Fill array
            for (i in 0..N - 1) {
                sequence[i] = (0..N).random()
            }
        }
    
        val FRACTION = 0.9
        // Create sorted sequence.
        // Once an element i is picked at random,
        // no element smaller than i can be picked.
        if (type == "sorted") {
            var r: Int
            r = (0..N).random()
            sequence[0] = r - (r * FRACTION).toInt()
            // Fill array
            for (i in 1..N - 1) {
                r = (sequence[i - 1]..N).random()
                sequence[i] = r - ((r - sequence[i - 1]) * FRACTION).toInt()
            }
        }
    
        // Create inv sorted sequence.
        // Once an element i is picked at random,
        // no element bigger than i can be picked.
        if (type == "inv") {
            var r: Int
            r = (0..N).random()
            sequence[0] = r + ((N - r) * FRACTION).toInt()
            // Fill array
            for (i in 1..N - 1) {
                r = (0..sequence[i - 1]).random()
                sequence[i] = r + ((sequence[i - 1] - r) * FRACTION).toInt()
            }
        }
    
        return sequence
    }
    
    fun insertionSort(A: Array<Int>): Array<Int> {
        var B = A // Copy A to another array, so it can be modified
        val N = B.size
        var j: Int
        var temp: Int
    
        for (i in 1..N-1) {
            j = i
            while (j != 0 && B[j] < B[j-1]) {
                // Swap the values
                temp = B[j]
                B[j] = B[j-1]
                B[j-1] = temp
                j--
            }
        }
    
        return B
    }

    val A = createSeq("random", 10000)
    start = System.currentTimeMillis()
    insertionSort(A)
    consumedTime = System.currentTimeMillis() - start
    println(consumedTime)
}