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
    var B = A
    val N = B.size
    for (i in 0..N-2){
        for (j in N-1 downTo i+1){
            if (B[j] < B[j-1]){
                // Swap the values
                var temp = B[j]
                B[j] = B[j-1]
                B[j-1] = temp
            }
        }
    }
    return B
}

// fun insertionSort(A: Array<Int>): Array<Int> {
 
// }

// fun selectionSort(A: Array<Int>) {
//  // Realizar!
// }

// fun shellSort(A: Array<Int>) {
//  // Realizar!
// }
