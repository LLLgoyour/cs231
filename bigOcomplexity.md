| Data Structure             | Time Complexity (Big O) |
| -------------------------- | ----------------------- | ------------------- | --------- | -------------- | ------------ | ------- | --------- | -------- |
|                            | Average                 | Worst Case          |
| -------------------------- | --------                | ------------------- |
|                            | Access                  | search              | insertion | deletion       | Access       | search  | insertion | deletion |
| -------------------------- | -------                 | --------            | ------    | ------         | ---------    | ------- | ------    | -------- |
| Array                      | O(1)                    | O(n)                | O(n)      | O(n)           | O(1)         | O(n)    | O(n)      | O(n)     |
| ArrayList                  | O(1)                    | O(n)                | O(n)      | O(1)           | O(1)         | O(n)    | O(1)      | O(1)     |
| LinkedList (singly linked) | O(1)                    | O(n)                | O(1)      | O(1)           |
| Stack (linked)             | O(n)                    | O(n)                | O(1)      | O(1)           | O(n)         | O(n)    | O(1)      | O(1)     |
| Queue (linked)             | O(1)                    | O(n)                | O(1)      | O(1)           | O(1)         | O(n)    | O(1)      | O(1)     |
| Hash Table                 | N/A                     | O(1)                | O(1)      | O(1)           | N/A          | O(n)    | O(n)      | O(n)     |
| Binary Heap                |
|                            | Storage                 | add vertex          | add edge  | remove vertex  | remove edge  | query   |
| -------------------------- | -------                 | -----------         | --------  | -------------- | ------------ | -----   |
| adjacency list             | O(V + E)                | O(1)                | O(1)      | O(V+E)         | O(E)         | O(V)    |
| adjacency matrix           | O(V\*2)                 | O(V^2)              | O(1)      | O(V^2)         | O(1)         | O(1)    |

adjacency list remove vertex:
