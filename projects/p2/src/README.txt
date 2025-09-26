Lab 2 Grid.java

1. To run the program, compile and execute Grid.java:
   javac Grid.java
   java Grid <rows> <cols> <positiveFlag> <values>

2. Command line arguments:
   - <rows>: number of rows in the grid (integer)
   - <cols>: number of columns in the grid (integer)
   - <positiveFlag>: boolean (true/false) indicating whether grid entries should be positive (true) or negative (false)
   - <values>: optional list of integers to fill the grid instead of random generation

3. Examples:
   - Run a 3x3 grid with positive random entries:
     java Grid 3 3 true

   - Run a 2x4 grid with negative entries:
     java Grid 2 4 false

   - Run a 2x2 grid with specific values:
     java Grid 2 2 true 1 2 3 4
