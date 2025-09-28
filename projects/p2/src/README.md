# Conway’s Game of Life

This project implements **Conway’s Game of Life** in Java using Swing for visualization.  
It contains the following key classes:

- **`Cell.java`**: Represents an individual cell that can be alive or dead.
- **`Landscape.java`**: Stores a 2D grid of `Cell` objects and manages updates.
- **`LandscapeDisplay.java`**: Handles the GUI rendering of the `Landscape`.
- **`LifeSimulation.java`**: Runs the simulation loop and connects all the components.

---

## How to Compile

From the root project source code folder, compile all `.java` files:

```bash
javac *.java

java LifeSimulation <rows> <cols> <steps>
```

Example usage:

```bash
java LifeSimulation 50 50 100
```
