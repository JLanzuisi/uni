#!/usr/bin/bash
# runSortlib.sh shell script wrapper around TestSort.jar
# Copyright (C) 2021  Jhonny Lanzuisi

# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.

# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.

help () {
    echo "runSortlib.sh shell script wrapper around TestSort.jar."
    echo "This program is Free Software, under the GPLv3+."
    echo "If there is no jar file, run make first."
    echo "This program compares the running time of 4 sorting algorithms:"
    echo "  -Bubble Sort"
    echo "  -Insertion Sort"
    echo "  -Selection Sort"
    echo "  -Shell Sort"
    echo ""
    echo "Usage: ./runSortlib.sh -s type -t attempts -n N"
    echo "  type. the type of the array to generate, possible values are: random, sorted, inv"
    echo "  attempts. number of attempts to sort the array. Must be a positive integer."
    echo "  N. amount of elements in the array to be sorted. Must be a positive integer."
    exit 1
}

invalid_arg () {
    echo "Invalid argument."
    echo "Use the -h flag to get help."
    exit 1
}

while getopts ":s:t:n:h" opt; do
  case ${opt} in
    s )
        type=$OPTARG
        ;;
    t )
        attempts=$OPTARG
        ;;
    n )
        N=$OPTARG
        ;;
    h )
        help
        ;;
    \? )
        echo "Invalid option: $OPTARG." 
        echo "Use the -h flag to get help."
        exit 1
        ;;
    : )
        echo "Invalid option: $OPTARG requires an argument." 
        echo "Use the -h flag to get help."
        exit 1
        ;;
  esac
done
shift $((OPTIND -1))

if [ -z "$type" ] || [ -z "$attempts" ] || [ -z "$N" ]
then
    echo "All options are mandatory."
    echo "Use the -h flag to get help."
    exit 1
fi

if [ "$type" != "random" ] && [ "$type" != "sorted" ] && [ "$type" != "inv" ]
then
    invalid_arg
fi

if ! [ "$attempts" -eq "$attempts" ] || [ "$attempts" -lt 1 ]
then
    invalid_arg
fi

if ! [ "$N" -eq "$N" ] || [ "$N" -lt 1 ]
then
    invalid_arg
fi

java -jar TestSort.jar $type $attempts $N