# Multi-Agent and Cellular-Automata Simulations

A Java coursework project implementing graphical simulations of cellular automata, boids and predator–prey swarm behavior, with a shared discrete-event engine and lightweight GUI-free regression checks.

## Academic context

The repository contains collaborative student work developed in a programming course. It also retains distributed teaching material, the GUI simulator library in `lib/gui.jar`, its documentation and third-party legal notices. Those components are not claimed as original work.

## Included simulations

- Conway's Game of Life
- Immigration cellular automata
- Schelling-style segregation
- Single- and multi-ball gravity animations
- Boids and predator–prey swarms
- A discrete-event scheduler used by the simulation engine

The cellular engine models toroidal neighborhoods and exposes simple state metrics. The swarm code combines flocking rules, predator targeting and scheduled updates. GUI entry points require the supplied `lib/gui.jar` library.

## Requirements and build

Use a JDK that supports the source level used by the repository, GNU Make and the tracked GUI library:

```bash
make build
```

This compiles all Java sources into `bin/` while preserving package directories. The GUI library is loaded from `lib/gui.jar`.

## Run

The Makefile provides these graphical targets:

```bash
make runGameOfLife
make runGameOfImmigration
make runSegregationSim
make runSwarm
make runTestBalls
make runTestGravityBall
make runInvader
```

They open GUI windows and are intended for an interactive desktop. They are not launched in headless CI.

## Tests and documentation

After `make build`, run the GUI-free cellular regression check with:

```bash
java -ea -cp "bin:lib/gui.jar" cellularSim.CellularEngineTest
```

Generate Javadoc without opening a browser with:

```bash
make javadoc-headless
```

The existing `make javadoc` target additionally attempts to open the generated index through `xdg-open`.

## My contribution

Git history attributes the latest simulation-engine refactor and metrics regression checks to Romain Arzel. The rest of the repository is collaborative; this section does not claim authorship of every simulation, GUI component or document.

## Limitations and academic integrity

This is an educational project, not a production simulation framework. Results depend on Java, GUI-library and desktop-environment versions; no performance, correctness or security guarantee is implied. Preserve the course attribution and the included third-party notices. No repository-wide license is added because ownership and redistribution rights for the course material and GUI library are not established.
