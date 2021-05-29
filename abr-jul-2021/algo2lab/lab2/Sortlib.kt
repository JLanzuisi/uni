/*
 * Sortlib.kt implement 6 sorting algorithms
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

fun bubbleSort(A: Array<Int>): Array<Int> {
    var B = A // Copy A to another array, so it can be modified
    val N = B.size
    var temp: Int

    for (i in 0..N-2) {
        for (j in N-1 downTo i+1) {
            if (B[j] < B[j-1]) {
                // Swap the values
                temp = B[j]
                B[j] = B[j-1]
                B[j-1] = temp
            }
        }
    }

    return B
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

fun selectionSort(A: Array<Int>): Array<Int> {
    var lowint: Int // current lowest integer found
    var lowindex: Int // position of lowint
    var B = A // Copy A to another array, so it can be modified
    val N = B.size
    var temp: Int

    for (i in 0..N-2) {
        lowindex = i
        lowint = B[i]
        for (j in i+1..N-1) {
            if (B[j] < lowint) {
                lowint = B[j]
                lowindex = j
            }
        }
        temp = B[i]
        B[i] = B[lowindex]
        B[lowindex] = temp
    }

    return B
}

fun shellSort(A: Array<Int>): Array<Int> {
    var j: Int
    var incr: Int
    var B = A // Copy A to another array, so it can be modified
    val N = B.size
    var temp: Int

    incr = N / 2
    while (incr > 0) {
        for (i in incr+1..N-1) {
            j = i - incr
            while(j > 0) {
                if (B[j] > B[j+incr]) {
                    temp = B[j]
                    B[j] = B[j+incr]
                    B[j+incr] = temp
                } else {
                    break
                }
            }
        }
        incr = incr / 2
    }

    return B
}

/*
 * Given two sorted arrays merge() merges them
 * into a third sorted array T. Returns T.
 */
fun merge(A: Array<Int>, B: Array<Int>): Array<Int> {
    var C = A + arrayOf(Integer.MAX_VALUE)// Copy A to another array, so it can be modified
    val N = A.size
    var D = B + arrayOf(Integer.MAX_VALUE) // Copy B to another array, so it can be modified
    val M = B.size
    var T = Array<Int>(N+M, {0})

    var i = 0
    var j = 0

    for (k in 0..N+M-1) {
        if (C[i] <= D[j] && C[i] < Int.MAX_VALUE && D[i] < Int.MAX_VALUE) {
            T[k] = C[i]
            i++
        } else if (C[i] > D[j]) {
            T[k] = D[j]
            j++
        } else {
            // Do nothing
        }
    }

    return T
}

/*
 * This version of merge sort uses insertion sort
 * when the length of the array is smaller than 10.
 */
fun mergeSortInsertion(A: Array<Int>): Array<Int> {
    var B = A // Copy A to another array, so it can be modified
    val N = B.size
    var firstHalf = Array<Int>(N/2, {0})
    var secondHalf = Array<Int>(Math.ceil(N.toDouble()/2).toInt(), {0})

    if (N <= 10) {
        B = insertionSort(B)
    } else {
        for (i in 0..N-1) {
            if (i < N/2) {
                firstHalf[i] = B[i]
            } else {
                secondHalf[i-N/2] = B[i]
            }
        }

        var firstHalfSorted = mergeSortInsertion(firstHalf)
        var secondHalfSorted = mergeSortInsertion(secondHalf)
        B = merge(firstHalfSorted, secondHalfSorted)
    }

    return B
}

/*
 * INCOMPLETE
 * This version of merge sort keeps dividing the array
 * until only arrays of length 1 are left, which are
 * odered by definition. 
 */
fun mergeSortIterative(A: Array<Int>): Array<Int> {
    var B = A // Copy A to another array, so it can be modified
    val N = B.size

    var k = 1
    var startL: Int
    var startR: Int

    if (N == 1) {
        return B
    }

    while (k < N) {
        startL = 0
        startR = k
        while (startR + k < N) {
            startL = startR + k
            startR = startL + k
        }
        k = 2*k
    }

    return B
}
