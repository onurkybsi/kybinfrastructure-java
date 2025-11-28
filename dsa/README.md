# DSA (Data Structures and Algorithms)

This module includes implementations for the common data structure and algorithms.

## Disjoint Set

### Backed by Linked List

|           | Time Complexity | Space Complexity |
| --------- | --------------- | ---------------- |
| `makeSet` | _O(1)_          | _O(1)_           |
| `find`    | _O(1)_          | _O(1)_           |
| `union`   | _O(min(n, m))_  | _O(1)_           |

### Backed by Tree

|           | Time Complexity | Amortized | Space Complexity |
| --------- | --------------- | --------- | ---------------- |
| `makeSet` | _O(1)_          | _O(1)_    | _O(1)_           |
| `find`    | _O(h)_          | _O(α(n))_ | _O(1)_           |
| `union`   | _O(h)_          | _O(α(n))_ | _O(1)_           |

where _α(n)_ is the [inverse-Ackermann function](https://en.wikipedia.org/wiki/Ackermann_function).
