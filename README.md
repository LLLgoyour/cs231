# CS-231: Data Structures and Algorithms

A comprehensive coursework repository demonstrating mastery of fundamental computer science concepts, including data structures, algorithms, complexity analysis, and their practical applications.

## Course Overview

CS-231 is an intensive computer science course that builds a strong foundation in data structures and algorithms. Throughout this course, I have implemented core data structures from scratch, analyzed algorithmic complexity, and applied these concepts to solve real-world programming challenges.

**Key Focus Areas:**

- Core data structures (Arrays, Linked Lists, Stacks, Queues, Hash Tables, Binary Search Trees, Heaps)
- Graph algorithms and traversal techniques
- Sorting algorithms and complexity analysis
- Object-oriented design and implementation
- Algorithm optimization and performance analysis

---

## Core Data Structures Implemented

### Linear Data Structures

#### **ArrayList**

- Generic, dynamically-resizing array implementation
- Automatic capacity doubling when threshold exceeded
- **Operations:** Add, Remove, Get, Set
- **Time Complexity:** O(1) amortized for append, O(n) for arbitrary insertion/deletion
- _Implemented in: [projects/p1/src/ArrayList.java](projects/p1/src/ArrayList.java)_

#### **LinkedList**

- Singly-linked list with node-based structure
- Efficient insertion/deletion at known positions
- **Operations:** Add, Remove, Insert, Traverse
- **Time Complexity:** O(1) for insertion/deletion at head, O(n) for arbitrary access
- _Implemented across multiple labs and projects_

#### **Stack**

- LIFO (Last-In-First-Out) data structure
- Linked list implementation with push/pop operations
- **Applications:** Postfix expression evaluation, DFS traversal
- _Implementations: [lecture/NodeStack.java](lecture/NodeStack.java), [labs/lab5/Stack.java](labs/lab5/Stack.java)_

#### **Queue**

- FIFO (First-In-First-Out) data structure
- Linked list implementation with enqueue/dequeue operations
- **Applications:** Server farm simulation, BFS traversal
- _Implementations: [labs/lab4/Queue.java](labs/lab4/Queue.java), [labs/lab7/Queue.java](labs/lab7/Queue.java)_

### Hierarchical Data Structures

#### **Binary Search Tree (BST)**

- Self-balancing tree for efficient searching
- **Operations:** Insert, Search, Delete, In-order Traversal
- **Time Complexity:** O(log n) average, O(n) worst case (unbalanced)
- _Implemented in: [lecture/] with sorting applications_

#### **Binary Heap**

- Min/Max heap implementation for priority queue operations
- Complete binary tree stored in array
- **Applications:** Priority queues, heap sort, event scheduling
- _Implemented in: [labs/lab7/Heap.java](labs/lab7/Heap.java)_

#### **Hash Table**

- Hash map implementation with collision handling
- **Operations:** Insert, Search, Delete
- **Time Complexity:** O(1) average for basic operations
- _Implemented in: [lecture/HashMap/HashMap.java](lecture/HashMap/HashMap.java)_

### Graph Data Structure

#### **Graph**

- Vertex and Edge abstraction with adjacency list representation
- Support for weighted edges and directed graphs
- **Operations:** Add vertex, Add edge, Graph traversal (DFS/BFS)
- **Storage:** O(V + E) with adjacency list
- _Implemented in: [labs/lab8/Graph.java](labs/lab8/Graph.java)_

---

## Projects & Applications

### **Project 1: Blackjack Card Game Engine**

_Demonstrates object-oriented design and generic data structures_

A complete implementation of the Blackjack card game with modular architecture and statistical analysis.

**Components:**

- **Card & Deck:** Immutable card objects with suit/rank representation
- **Hand:** Collection of cards with score calculation (Ace handling for 1 and 11)
- **Blackjack:** Game logic with betting and dealer mechanics
- **ArrayList:** Generic collection for storing deck and hand cards
- **Simulation & Analysis:** Statistical analysis of game outcomes

**Key Accomplishments:**

- Generic ArrayList implementation supporting arbitrary types
- Comprehensive test suite with unit tests for all components
- Simulation of 10,000+ games to analyze win/loss probabilities
- Extension exercises for advanced betting strategies

_Location: [projects/p1/src/](projects/p1/src/)_

### **Project 2-8: Advanced Data Structures & Algorithms**

Ongoing specialized projects focusing on specific data structure applications and optimizations.

---

## Laboratory Exercises

### **Lab 1: ArrayList & Shuffle Algorithm**

- Generic ArrayList implementation
- Fisher-Yates shuffle algorithm implementation
- Testing and performance analysis

### **Lab 2: 2D Grid Operations**

- Multi-dimensional array manipulation
- Row and column operations
- Grid search and pattern matching

### **Lab 3: Linked List Deep Dive**

- Comprehensive LinkedList implementation
- Extensive unit testing
- Comparison with ArrayList performance characteristics

### **Lab 4: Server Farm Simulation with Queues**

- Event-driven simulation system
- Job queue management
- Multi-server scheduling
- Performance metrics collection

### **Lab 5: Stack Applications**

- Stack-based algorithms
- Maze solving with DFS
- Board state representation

### **Lab 6: Binary Search Trees**

- BST insertion and search
- Tree traversal methods
- Balancing considerations

### **Lab 7: Heap & Priority Queues**

- Max/Min heap implementation
- Priority queue operations
- Heap sort algorithm

### **Lab 8: Graph Algorithms**

- Graph representation (adjacency list)
- Vertex and Edge abstractions
- Graph traversal foundations

---

## Complexity Analysis Reference

### Time Complexity Comparison

| Data Structure | Average Access | Search    | Insertion | Deletion  |
| -------------- | -------------- | --------- | --------- | --------- |
| Array          | O(1)           | O(n)      | O(n)      | O(n)      |
| ArrayList      | O(1)           | O(n)      | O(n)\*    | O(n)\*    |
| LinkedList     | O(n)           | O(n)      | O(1)†     | O(1)†     |
| Stack (Linked) | O(n)           | O(n)      | O(1)      | O(1)      |
| Queue (Linked) | O(n)           | O(n)      | O(1)      | O(1)      |
| Hash Table     | N/A            | O(1)‡     | O(1)‡     | O(1)‡     |
| Binary Heap    | N/A            | O(n)      | O(log n)  | O(log n)  |
| BST            | O(log n)§      | O(log n)§ | O(log n)§ | O(log n)§ |

_\*O(1) amortized at end, O(n) for arbitrary positions_
_†At known position (O(1) at head)_
_‡Average case; O(n) worst case with poor hashing_
_§Average case; O(n) worst case if unbalanced_

### Sorting Algorithm Complexity

| Algorithm      | Best Case  | Average Case | Worst Case | Space    |
| -------------- | ---------- | ------------ | ---------- | -------- |
| Merge Sort     | O(n log n) | O(n log n)   | O(n log n) | O(n)     |
| Heap Sort      | O(n log n) | O(n log n)   | O(n log n) | O(1)     |
| Quick Sort     | O(n log n) | O(n log n)   | O(n²)      | O(log n) |
| Selection Sort | O(n²)      | O(n²)        | O(n²)      | O(1)     |
| Bubble Sort    | O(n)       | O(n²)        | O(n²)      | O(1)     |

---

## Key Concepts & Techniques Mastered

### **Object-Oriented Programming**

- Generic class implementations with type parameters `<T>`
- Encapsulation with private data and public interfaces
- Interface implementation and polymorphism
- Comprehensive unit testing frameworks

### **Algorithmic Thinking**

- **Divide & Conquer:** Merge sort, quick sort implementations
- **Greedy Algorithms:** Selection, optimal substructure
- **Dynamic Programming:** Graph algorithms, optimization
- **Graph Traversal:** DFS, BFS for pathfinding

### **Performance Optimization**

- Amortized analysis for dynamic arrays
- Cache optimization with array-based heaps
- Trade-offs between time and space complexity
- Benchmarking and profiling techniques

### **Software Engineering Practices**

- Comprehensive test-driven development
- Code documentation and comments
- Modular design for reusability
- Version control and collaborative development

---

## Repository Structure

```
CS-231/
├── homework/              # Weekly homework assignments
│   ├── hw2/              # Probability simulations, coin flips
│   ├── hw3/              # Algorithm analysis problems
│   ├── hw4/              # LinkedList implementation exercises
│   └── hw6/              # Postfix expression conversion
│
├── labs/                 # 8 comprehensive laboratory projects
│   ├── lab1/            # ArrayList & Shuffle
│   ├── lab2/            # 2D Grid operations
│   ├── lab3/            # Linked List comprehensive testing
│   ├── lab4/            # Queue-based server farm simulation
│   ├── lab5/            # Stack applications and maze solving
│   ├── lab6/            # Binary Search Trees
│   ├── lab7/            # Heaps and priority queues
│   └── lab8/            # Graphs and traversal
│
├── projects/            # Major application projects
│   ├── p0/              # Foundation exercises
│   ├── p1/              # Blackjack card game engine
│   └── p2-p8/           # Advanced specialized projects
│
├── lecture/             # Reference implementations
│   ├── LinkedList.java
│   ├── MyIntArrayList.java
│   ├── Queue.java
│   ├── TicTacToe.java
│   ├── DFS/            # Depth-First Search examples
│   ├── Graph/          # Graph algorithms (Dijkstra, MST)
│   ├── HashMap/        # Hash table implementation
│   └── Heap/           # Priority queue and heap sort
│
├── bigOcomplexity.md   # Complete Big O reference guide
├── SortingAlgorithms.md # Sorting algorithm complexity analysis
├── notes.md             # Course notes and Java fundamentals
└── README.md           # This file
```

---

## Learning Outcomes

By completing this coursework, I have demonstrated:

**Strong Foundation in Data Structures**

- Can implement core data structures from scratch
- Understand trade-offs between different data structure choices
- Know when to apply each structure for optimal performance

**Algorithm Analysis & Complexity**

- Proficient in Big O notation and complexity analysis
- Can identify optimal algorithms for specific problems
- Understand amortized analysis and real-world performance

**Object-Oriented Design**

- Write modular, reusable, maintainable code
- Implement generic classes with type parameters
- Design comprehensive test suites

**Problem-Solving Skills**

- Break down complex problems into manageable components
- Recognize patterns and apply appropriate algorithms
- Debug and optimize code for performance

**Software Engineering Practices**

- Version control and collaborative development
- Code documentation and technical communication
- Test-driven development methodology

---

## Running the Projects

### Prerequisites

- Java 8 or higher
- javac compiler

### Compilation & Execution

**Generic Project Compilation:**

```bash
cd projects/p1/src
javac *.java
java Blackjack
```

**Lab Exercises:**

```bash
cd labs/lab4/src
javac *.java
java DispatcherTests
```

**Running Tests:**

```bash
# Most labs include comprehensive test suites
javac *Tests.java
java [TestClassName]
```

---

## Technical References

- **Big O Complexity Guide:** [bigOcomplexity.md](bigOcomplexity.md)
- **Sorting Algorithms Analysis:** [SortingAlgorithms.md](SortingAlgorithms.md)
- **Course Notes:** [notes.md](notes.md)

---

## License

This project is provided under the [LICENSE](LICENSE) file included in the repository.

---

**Last Updated:** April 2026

This repository represents a comprehensive exploration of fundamental computer science concepts with practical implementations demonstrating both theoretical knowledge and practical coding skills.
