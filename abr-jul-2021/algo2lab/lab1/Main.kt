/*
 * Main.kt test and benchmark sorting algorithms
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
 * args[2] = number of elements of the sequence
 */

fun main(args: Array<String>) {
    val N = args[2].toInt()
    val type = args[0]

    val B = createSeq(type, N) // Gen testing sequence

    val attempts = args[1].toInt()
    // *time arrays hold execution time of that algorithm in miliseconds
    var bubbleTime = Array<Long>(attempts, { 0 })
    var insertionTime = Array<Long>(attempts, { 0 })
    var selectionTime = Array<Long>(attempts, { 0 })
    var shellTime = Array<Long>(attempts, { 0 })
    // for measuring time
    var start: Long
    var consumedTime: Long

    for (i in 0..attempts - 1) {
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

    // If more than 1 attempts, display standard deviation
    if (attempts > 1) {
        // Convert time arrays to Double so that they can be
        // given to calculateSD()
        var bubbleTimeDouble = Array<Double>(attempts, { 0.0 })
        var insertionTimeDouble = Array<Double>(attempts, { 0.0 })
        var selectionTimeDouble = Array<Double>(attempts, { 0.0 })
        var shellTimeDouble = Array<Double>(attempts, { 0.0 })
        for (i in 0..attempts - 1) {
            bubbleTimeDouble[i] = bubbleTime[i].toDouble()
            insertionTimeDouble[i] = insertionTime[i].toDouble()
            selectionTimeDouble[i] = selectionTime[i].toDouble()
            shellTimeDouble[i] = shellTime[i].toDouble()
        }
        println("STANDARD DEVIATION (miliseconds)")
        println("Bubble Sort: " + calculateSD(bubbleTimeDouble))
        println("Insertion Sort: " + calculateSD(insertionTimeDouble))
        println("Selection Sort: " + calculateSD(selectionTimeDouble))
        println("Shell Sort: " + calculateSD(shellTimeDouble))
    }
}
