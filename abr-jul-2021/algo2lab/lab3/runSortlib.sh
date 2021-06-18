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
    echo "runSortlib.sh is a shell script wrapper around TestSort.jar."
    echo "This program is Free Software, under the GPLv3+."
    echo "If there is no jar file, run make first."
    echo "This program compares the running time of 7 sorting algorithms:"
    echo "  -Bubble Sort"
    echo "  -Insertion Sort"
    echo "  -Selection Sort"
    echo "  -Shell Sort"
    echo "  -Merge Sort (Insertion)"
    echo "  -Merge Sort (Iterative)"
    echo "  -Heapsort"
    echo ""
    echo "Usage: ./runSortlib.sh -s type -t attempts -n 'seqSizes' -a algName -g plotFileName"
    echo "  type. The type of the array to generate, possible values are: random, sorted, inv"
    echo "  attempts. Number of attempts to sort the array. Must be a positive integer."
    echo "  seqSizes. Amount of elements in each array to be sorted. All numbers must be positive integers."
    echo "  algName. Set of algorithms to test. Possible values are:"
    echo "      0n2: Bubble Sort, Insertion Sort, Selection Sort, Shell Sort"
    echo "      nlgn: Merge Sort (Insertion), Merge Sort (Iterative)"
    echo "      all: All of the above."
    echo "  plotFileName. Name of the output file. Only makes sense if -n has more than one argument. "
    echo ""
    echo "Examples:"
    echo "  runSortlib.sh -t 2 -s random -n 500 -a nlgn"
    echo "  runSortlib.sh -s inv -n 20 40 50 -t 3 -a On2 -g salidaInv"
    exit 1
}

while getopts ":h" opt; do
  case ${opt} in
    # s )
    #     type=$OPTARG
    #     ;;
    # t )
    #     attempts=$OPTARG
    #     ;;
    # n )
    #     seqSizes+=($OPTARG)
    #     ;;
    # g )
    #     plotFileName=$OPTARG
    #     ;;
    # a )
    #     algName=$OPTARG
    #     ;;
    h )
        help
        ;;
    # \? )
    #     echo "Invalid option: $OPTARG." 
    #     echo "Use the -h flag to get help."
    #     exit 1
    #     ;;
    # : )
    #     echo "Invalid option: $OPTARG requires an argument." 
    #     echo "Use the -h flag to get help."
    #     exit 1
    #     ;;
  esac
done
#shift $((OPTIND -1))

# # Check for mandatory options
# if [ -z "$type" ] || [ -z "$attempts" ] || [ -z "$seqSizes" ] || [ -z "$algName" ]
# then
#     echo "Options -s -t -n -a are mandatory."
#     echo "Use the -h flag to get help."
#     exit 1
# fi

# # Check correct values of variables 'type'
# if [ "$type" != "random" ] && [ "$type" != "sorted" ] && [ "$type" != "inv" ]
# then
#     invalid_arg
# fi

# # Make sure attempts is an integer greater than 1
# if ! [ "$attempts" -eq "$attempts" ] || [ "$attempts" -lt 1 ]
# then
#     invalid_arg
# fi

# # If option -g is given with only 1 argument to -n, then error
# if [ ${#seqSizes[@]} == 1 ] && [ -n "$plotFileName" ]
# then
#     invalid_arg
# fi

# # Check that all arguments of -n are integers greater than 1
# for N in ${seqSizes[@]}; do
#     if ! [ "$N" -eq "$N" ] || [ "$N" -lt 1 ]
#     then
#         invalid_arg
#     fi
# done

# # Check that all arguments to -n are unique
# seqSizesUniques=`echo "${seqSizes[@]}" | tr ' ' '\n' | sort | uniq | wc -l`
# if [ $seqSizesUniques != ${#seqSizes[@]} ]
# then
#     invalid_arg
# fi

# # Check correct values of variable 'algName'
# if [ $algName != "0n2" ] && [ $algName != "nlgn" ] && [ $algName != "all" ]
# then
#     invalid_arg
# fi

LIBDIRS="lib/lets-plot-jfx-2.0.2.jar:lib/lets-plot-kotlin-api-2.0.1.jar:lib/lets-plot-image-export-2.0.2.jar:lib/batik-all-1.12.jar:lib/w3c.jar:lib/kotlin-logging-1.12.4.jar:lib/slf4j-api-1.7.30.jar:lib/slf4j-simple-1.7.30.jar:lib/jaxp-1.4.jar:lib/sac.jar:lib/xmlgraphics-commons-1.5.jar:lib/javafx-swing-11.jar:lib/javafx-graphics-11.jar:lib/javafx-base-11.jar:."

#echo "$*"
kotlin -cp $LIBDIRS MainKt $*