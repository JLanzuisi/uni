/*
 * Sortlib.kt implement 4 sorting algorithms
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

    for (i in 0..N-2){
        for (j in N-1 downTo i+1){
            if (B[j] < B[j-1]){
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

    for (i in 1..N-1){
        j = i
        while (j != 0 && B[j] < B[j-1]){
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

    for (i in 0..N-2){
        lowindex = i
        lowint = B[i]
        for (j in i+1..N-1){
            if (B[j] < lowint){
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
    while (incr > 0){
        for (i in incr+1..N-1){
            j = i - incr
            while(j > 0){
                if (B[j] > B[j+incr]){
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
