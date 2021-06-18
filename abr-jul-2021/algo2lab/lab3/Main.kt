/*
 * Main.kt test, benchmark and graph sorting algorithms
 * Copyright (C) 2021  Jhonny Lanzuisi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

import kotlin.system.exitProcess
import kotlin.system.measureNanoTime

/*
 * createSeq() takes a type of sequence (random, sorted, inv)
 * and creates a sequence of N elements of the type specified.
 * It return the created sequence.
 */
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

/*
 * isSorted() takes an array of integers and
 * returns true if it is sorted in ascending order.
 * It returns false otherwise.
 */
fun isSorted(A: Array<Int>): Boolean {
    var sorted: Boolean = true
    val N = A.size

    for (i in 0..N - 2) {
        if (A[i] > A[i + 1]) {
            sorted = false
            break
        }
    }
    return sorted
}

/*
 * calculateSD() takes an array of doubles
 * and calculates its standard deviation
 */
fun calculateSD(numArray: Array<Double>): Double {
    var standardDeviation = 0.0

    val mean = numArray.average()

    for (num in numArray) {
        standardDeviation += Math.pow(num - mean, 2.0)
    }

    return Math.sqrt(standardDeviation / 10)
}

/*
 * linearSearch() searches for the string "key"
 * in the provided array, and returns its position
 * or -1 if not found. It errors if the string is duplicated.
 */
fun linearSearch(A: Array<String>, key: String): Int {
    val N = A.size
    var keyindex = -1

    for (i in 0..N-1) {
        if (A[i] == key && keyindex != -1) {
            println("Duplicate option:"+A[i])
            exitProcess(1)
        }
        if (A[i] == key) {
            keyindex = i
        }
    }

    return keyindex
}

/*
 * argList[0] = type of sequence
 * argList[1] = number of tries
 * argList[2] = set of algorithms
 * argList[3] = plot file name
 * argList[4..n] = sequence sizes
 */
fun getArgs(A: Array<String>): Array<String> {
    val N = A.size
    var argListSize = N-4
    val gIndex = linearSearch(A, "-g")
    if (gIndex == -1) {
        argListSize = N-3
    }

    var argList = Array<String>(argListSize, {""})

    if (N == 0) {
        println("All options are mandatory. Use -h flag for help.")
        exitProcess(1)
    }

    fun invalidArg() {
        println("Invalid argument. Use -h flag for help.")
        exitProcess(1)
    }

    fun checkOutBounds(index: Int) {
        try {
            (A[index] == A[index])
        } catch (e: ArrayIndexOutOfBoundsException) {
            invalidArg()
        }
    }

    fun isInteger(s: String): Boolean {
        val test = s.toIntOrNull()
        if (test == null) {
            return false
        } else {
            return true
        }
    }

    val sIndex = linearSearch(A, "-s")
    if (sIndex == -1) invalidArg()
    checkOutBounds(sIndex + 1)
    if (A[sIndex + 1] != "random" && 
        A[sIndex + 1] != "inv" && 
        A[sIndex + 1] != "sorted"
    ) invalidArg()
    argList[0] = A[sIndex + 1]

    val tIndex = linearSearch(A, "-t")
    if (tIndex == -1) invalidArg()
    checkOutBounds(tIndex + 1)
    if (!isInteger(A[tIndex + 1])) invalidArg()
    if (A[tIndex + 1].toInt() < 0) invalidArg()
    argList[1] = A[tIndex + 1]

    val aIndex = linearSearch(A, "-a")
    if (aIndex == -1) invalidArg()
    checkOutBounds(aIndex+1)
    if (A[aIndex + 1] != "0n2" && 
        A[aIndex + 1] != "nlgn" && 
        A[aIndex + 1] != "all"
    ) invalidArg()
    argList[2] = A[aIndex + 1]

    if (gIndex != -1) {
        checkOutBounds(gIndex + 1)
        if (N == 10) invalidArg()
        argList[3] = A[gIndex + 1]
    }

    val nIndex = linearSearch(A, "-n")
    if (nIndex == -1) invalidArg()
    var i = 1
    checkOutBounds(nIndex + i)
    if (A[nIndex+i] == "-t" ||
        A[nIndex+i] == "-s" ||
        A[nIndex+i] == "-a" ||
        A[nIndex+i] == "-g"
    ) invalidArg()
    while (true) {
        if (i == N) break
        if (A[nIndex+i] == "-t" ||
            A[nIndex+i] == "-s" ||
            A[nIndex+i] == "-a" ||
            A[nIndex+i] == "-g"
        ) break
        if (!isInteger(A[nIndex + i])) invalidArg()
        if (A[tIndex + 1].toInt() < 0) invalidArg()
        argList[3+i] = A[nIndex+i]
        i++
    }

    return argList
}

fun main(args: Array<String>) {
    val arguments = getArgs(args)
    val N = arguments.size - 4
    println(N)
    for (i in arguments) println(i)
    val seq = Array<Int>(N, {0})
    val type = arguments[0]
    var filename = "picture"
    if (arguments[3] != "") {
        filename = arguments[3]+".png"
    }

    // Generate array of sequence sizes

    for (i in 0..N-1) {
        seq[i] = arguments[4+i].toInt()
    }

    var algNum: Int
    if (arguments[2] == "0n2") {
        algNum = 4
    } else if (arguments[2] == "nlgn") {
        algNum = 2
    } else {
        algNum = 6
    }

    val attempts = arguments[1].toInt()
    // *time arrays hold execution time of that algorithm in miliseconds
    var bubbleTime = Array<Long>(attempts, { 0 })
    var insertionTime = Array<Long>(attempts, { 0 })
    var selectionTime = Array<Long>(attempts, { 0 })
    var shellTime = Array<Long>(attempts, { 0 })
    var mergeSortInsertionTime = Array<Long>(attempts, { 0 })
    var mergeSortIterativeTime = Array<Long>(attempts, { 0 })
    // Average, min and max times
    var averageTimes = Array<Double>(N*algNum, {0.0})
    var minTimes = Array<Double>(N*algNum, {0.0})
    var maxTimes = Array<Double>(N*algNum, {0.0})
    // Alg names
    var algorithmsLabels = Array<String>(N*algNum, {""})
    // Sequence sizes
    var numElements = Array<Int>(N*algNum, {0})

    for (k in 0..N-1) {
        val B = createSeq(type, seq[k]) // Gen testing sequence

        // This for loop gathers all the runtimes
        for (i in 0..attempts-1) {
            if (algNum == 4 || algNum == 6) {
                val bubbleSorted = bubbleSort(B)
                val consumedTimeBubble = measureNanoTime { bubbleSort(B) }
                if (!isSorted(bubbleSorted)) {
                    println("Array did not get sorted correctly. Terminating.")
                    exitProcess(1)
                }
                bubbleTime[i] = consumedTimeBubble / 1000000000
    
                val insertionSorted = insertionSort(B)
                val consumedTimeInsertion = measureNanoTime { bubbleSort(B) }
                if (!isSorted(insertionSorted)) {
                    println("Array did not get sorted correctly. Terminating.")
                    exitProcess(1)
                }
                insertionTime[i] = consumedTimeInsertion / 1000000000
    
                val selectionSorted = selectionSort(B)
                val consumedTimeSelection = measureNanoTime { selectionSort(B) }
                if (!isSorted(selectionSorted)) {
                    println("Array did not get sorted correctly. Terminating.")
                    exitProcess(1)
                }
                selectionTime[i] = consumedTimeSelection / 1000000000
    
                val shellSorted = shellSort(B)
                val consumedTimeShell = measureNanoTime { shellSort(B) }
                if (!isSorted(shellSorted)) {
                    println("Array did not get sorted correctly. Terminating.")
                    exitProcess(1)
                }
                shellTime[i] = consumedTimeShell / 1000000000
            }

            if (algNum == 2 || algNum == 6) {
                val mergeInsertionSorted = mergeSortInsertion(B)
                val consumedTimeMergeInsertion = measureNanoTime { mergeSortInsertion(B) }
                if (!isSorted(mergeInsertionSorted)) {
                    println("Array did not get sorted correctly. Terminating.")
                    exitProcess(1)
                }
                mergeSortInsertionTime[i] = consumedTimeMergeInsertion / 1000000000
    
                // NOT IMPLEMENTED
                val mergeIterativeSorted = mergeSortIterative(B)
                val consumedTimeMergeIterative = measureNanoTime { mergeSortIterative(B) }
                // if (!isSorted(mergeIterativeSorted)) {
                //     println("Array did not get sorted correctly. Terminating.")
                //     exitProcess(1)
                // }
                mergeSortIterativeTime[i] = consumedTimeMergeIterative / 1000000000
            }
        }

        if (algNum == 4) {
            // Names
            algorithmsLabels[k] = "Bubble Sort"
            algorithmsLabels[k+N] = "Insertion Sort"
            algorithmsLabels[k+2*N] = "Selection Sort"
            algorithmsLabels[k+3*N] = "Shell Sort"
            // Times
            averageTimes[k] = bubbleTime.average()
            averageTimes[k+N] = insertionTime.average()
            averageTimes[k+2*N] = selectionTime.average()
            averageTimes[k+3*N] = shellTime.average()
            minTimes[k] = bubbleTime.minOrNull()!!.toDouble()
            minTimes[k+N] = insertionTime.minOrNull()!!.toDouble()
            minTimes[k+2*N] = selectionTime.minOrNull()!!.toDouble()
            minTimes[k+3*N] = shellTime.minOrNull()!!.toDouble()
            maxTimes[k] = bubbleTime.maxOrNull()!!.toDouble()
            maxTimes[k+N] = insertionTime.maxOrNull()!!.toDouble()
            maxTimes[k+2*N] = selectionTime.maxOrNull()!!.toDouble()
            maxTimes[k+3*N] = shellTime.maxOrNull()!!.toDouble()
            // Elements
            numElements[k] = seq[k]
            numElements[k+N] = seq[k]
            numElements[k+2*N] = seq[k]
            numElements[k+3*N] = seq[k]
        } else if (algNum == 2) {
            // Names
            algorithmsLabels[k] = "Merge Sort (Insertion)"
            algorithmsLabels[k+N] = "Merge Sort (Iterative)"
            // Times
            averageTimes[k] = mergeSortInsertionTime.average()
            averageTimes[k+N] = mergeSortIterativeTime.average()
            minTimes[k] = mergeSortInsertionTime.minOrNull()!!.toDouble()
            minTimes[k+N] = mergeSortIterativeTime.minOrNull()!!.toDouble()
            maxTimes[k] = mergeSortInsertionTime.maxOrNull()!!.toDouble()
            maxTimes[k+N] = mergeSortIterativeTime.maxOrNull()!!.toDouble()
            // Elements
            numElements[k] = seq[k]
            numElements[k+N] = seq[k]
        } else {
            // All of the above
            // Names
            algorithmsLabels[k] = "Bubble Sort"
            algorithmsLabels[k+N] = "Insertion Sort"
            algorithmsLabels[k+2*N] = "Selection Sort"
            algorithmsLabels[k+3*N] = "Shell Sort"
            algorithmsLabels[k+4*N] = "Merge Sort (Insertion)"
            algorithmsLabels[k+5*N] = "Merge Sort (Iterative)"
            // Times
            averageTimes[k] = bubbleTime.average()
            averageTimes[k+N] = insertionTime.average()
            averageTimes[k+2*N] = selectionTime.average()
            averageTimes[k+3*N] = shellTime.average()
            averageTimes[k+4*N] = mergeSortInsertionTime.average()
            averageTimes[k+5*N] = mergeSortIterativeTime.average()
            minTimes[k] = bubbleTime.minOrNull()!!.toDouble()
            minTimes[k+N] = insertionTime.minOrNull()!!.toDouble()
            minTimes[k+2*N] = selectionTime.minOrNull()!!.toDouble()
            minTimes[k+3*N] = shellTime.minOrNull()!!.toDouble()
            minTimes[k+4*N] = mergeSortInsertionTime.minOrNull()!!.toDouble()
            minTimes[k+5*N] = mergeSortIterativeTime.minOrNull()!!.toDouble()
            maxTimes[k] = bubbleTime.maxOrNull()!!.toDouble()
            maxTimes[k+N] = insertionTime.maxOrNull()!!.toDouble()
            maxTimes[k+2*N] = selectionTime.maxOrNull()!!.toDouble()
            maxTimes[k+3*N] = shellTime.maxOrNull()!!.toDouble()
            maxTimes[k+4*N] = mergeSortInsertionTime.maxOrNull()!!.toDouble()
            maxTimes[k+5*N] = mergeSortIterativeTime.maxOrNull()!!.toDouble()
            // Elements
            numElements[k] = seq[k]
            numElements[k+N] = seq[k]
            numElements[k+2*N] = seq[k]
            numElements[k+3*N] = seq[k]
            numElements[k+4*N] = seq[k]
            numElements[k+5*N] = seq[k]
        }
    }

    if (N > 1 && filename != "") {
        // Use all the previous information to plot 
        // running times vs the amount of elements
        plotRuntime("Graficas de tiempo de algoritmos de ordenamiento",
        ".",
        filename,
        "Resultados de la secuencia de números aleatorios",
        "Número de elementos",
        "Tiempo (seg)",
        algorithmsLabels,
        numElements,
        minTimes,
        averageTimes,
        maxTimes)
    }

    // If there's only one sequence and all algorithms,
    // then print average times and SD as before.
    if (N == 1 && algNum == 6) {
        // Put corresponding label depending on
        // whether there are multiple attempts
        if (attempts > 1) {
            println("AVERAGE TIME (miliseconds)")
        } else {
            println("TIME (miliseconds)")
        }
        println("Bubble Sort: " + bubbleTime.average())
        println("Insertion Sort: " + insertionTime.average())
        println("Selection Sort: " + selectionTime.average())
        println("Shell Sort: " + shellTime.average())
        println("Merge Sort (Insertion)" + mergeSortInsertionTime.average())
        println("Merge Sort (Iterative)" + mergeSortIterativeTime.average())

        // If more than 1 attempts, display standard deviation
        if (attempts > 1) {
            // Convert time arrays to Double so that they can be
            // given to calculateSD()
            var bubbleTimeDouble = Array<Double>(attempts, { 0.0 })
            var insertionTimeDouble = Array<Double>(attempts, { 0.0 })
            var selectionTimeDouble = Array<Double>(attempts, { 0.0 })
            var shellTimeDouble = Array<Double>(attempts, { 0.0 })
            var mergeSortInsertionTimeDouble = Array<Double>(attempts, {0.0})
            var mergeSortIterativeTimeDouble = Array<Double>(attempts, {0.0})
            for (i in 0..attempts - 1) {
                bubbleTimeDouble[i] = bubbleTime[i].toDouble()
                insertionTimeDouble[i] = insertionTime[i].toDouble()
                selectionTimeDouble[i] = selectionTime[i].toDouble()
                shellTimeDouble[i] = shellTime[i].toDouble()
                mergeSortInsertionTimeDouble[i] = mergeSortInsertionTime[i].toDouble()
                mergeSortIterativeTimeDouble[i] = mergeSortIterativeTime[i].toDouble()
            }
            println("STANDARD DEVIATION (miliseconds)")
            println("Bubble Sort: " + calculateSD(bubbleTimeDouble))
            println("Insertion Sort: " + calculateSD(insertionTimeDouble))
            println("Selection Sort: " + calculateSD(selectionTimeDouble))
            println("Shell Sort: " + calculateSD(shellTimeDouble))
            println("Merge Sort Insertion:" + calculateSD(mergeSortInsertionTimeDouble))
            println("Merge Sort Iterative:" + calculateSD(mergeSortIterativeTimeDouble))
        }
    }
}
