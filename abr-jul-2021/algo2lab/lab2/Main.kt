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
 * args[0] = type of sequence
 * args[1] = number of tries
 * args[2] = amount of sequence sizes
 * args[3..n] = number of elements of the sequences
 * args[n+1] = set of algorithms to run
 * args[n+2] = file name of image
 */

fun main(args: Array<String>) {
    val N = args[2].toInt()
    val seq = Array<Int>(N, {0})
    val type = args[0]
    var filename = ""
    if (args.size == N+5) {
        filename = args[N+4]+".png"
    }

    // Generate array of sequences
    for (i in 0..N-1) {
        seq[i] = args[i+3].toInt()
    }

    var algNum: Int
    if (args[3+N] == "0n2") {
        algNum = 4
    } else if (args[3+N] == "nlgn") {
        algNum = 2
    } else {
        algNum = 6
    }

    val attempts = args[1].toInt()
    // *time arrays hold execution time of that algorithm in miliseconds
    var bubbleTime = Array<Long>(attempts, { 0 })
    var insertionTime = Array<Long>(attempts, { 0 })
    var selectionTime = Array<Long>(attempts, { 0 })
    var shellTime = Array<Long>(attempts, { 0 })
    var mergeSortInsertionTime = Array<Long>(attempts, { 0 })
    var mergeSortIterativeTime = Array<Long>(attempts, { 0 })
    // for measuring time
    var start: Long
    var consumedTime: Long
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
                start = System.currentTimeMillis()
                val bubbleSorted = bubbleSort(B)
                consumedTime = System.currentTimeMillis() - start
                if (!isSorted(bubbleSorted)) {
                    println("Array did not get sorted correctly. Terminating.")
                    exitProcess(1)
                }
                bubbleTime[i] = consumedTime
    
                start = System.currentTimeMillis()
                val insertionSorted = insertionSort(B)
                consumedTime = System.currentTimeMillis() - start
                if (!isSorted(insertionSorted)) {
                    println("Array did not get sorted correctly. Terminating.")
                    exitProcess(1)
                }
                insertionTime[i] = consumedTime
    
                start = System.currentTimeMillis()
                val selectionSorted = selectionSort(B)
                consumedTime = System.currentTimeMillis() - start
                if (!isSorted(selectionSorted)) {
                    println("Array did not get sorted correctly. Terminating.")
                    exitProcess(1)
                }
                selectionTime[i] = consumedTime
    
                start = System.currentTimeMillis()
                val shellSorted = shellSort(B)
                consumedTime = System.currentTimeMillis() - start
                if (!isSorted(shellSorted)) {
                    println("Array did not get sorted correctly. Terminating.")
                    exitProcess(1)
                }
                shellTime[i] = consumedTime
            }

            if (algNum == 2 || algNum == 6) {
                start = System.currentTimeMillis()
                val mergeInsertionSorted = mergeSortInsertion(B)
                consumedTime = System.currentTimeMillis() - start
                if (!isSorted(mergeInsertionSorted)) {
                    println("Array did not get sorted correctly. Terminating.")
                    exitProcess(1)
                }
                mergeSortInsertionTime[i] = consumedTime
    
                // NOT IMPLEMENTED
                start = System.currentTimeMillis()
                val mergeIterativeSorted = mergeSortIterative(B)
                consumedTime = System.currentTimeMillis() - start
                // if (!isSorted(mergeIterativeSorted)) {
                //     println("Array did not get sorted correctly. Terminating.")
                //     exitProcess(1)
                // }
                mergeSortIterativeTime[i] = consumedTime
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
