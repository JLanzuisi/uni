def sum_less_than(N: int, integers: [int]) -> bool:
  """
  Return true if the sum of integers is smaller than N.
  Return false otherwise.
  """
  if sum(integers) <= N:
    return True
  else:
    return False

def lexicographic_partition(N: int) -> None:
  """
  Print the partitions of N in lexicographic order to stout.
  """
  assert N > 0, "Input must be a positive integer"

  import sympy

  number_of_partitions = sympy.partition(N)
  current_partition = []

  for i in range(0, number_of_partitions-1):
    if i == 0:
      current_partition = [N]
    if i > 0 and len(current_partition) != N:
      if current_partition[-1] != 1:
        current_partition[-1] -= 1
        current_partition += [1]
      else:
        if current_partition[0] != 1:
          while current_partition[-1] == 1:
            del current_partition[-1]
        current_partition[-1] -= 1
        while True and len(current_partition) != N:
          current_partition_plus_last = current_partition + [current_partition[-1]]
          if sum_less_than(N, current_partition_plus_last):
            current_partition = current_partition_plus_last
          else:
            break
        if sum(current_partition) != N:
          current_partition += [N - sum(current_partition)]
          
    print(current_partition)
    i += 1

lexicographic_partition(8)
