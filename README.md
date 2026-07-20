# Multi-Agent and Cellular-Automata Simulations

## Overview

An educational Java project containing graphical simulations of cellular automata, balls, boids and predator–prey swarms. It also includes a shared discrete-event scheduler and a GUI-free cellular regression check.

## Academic context

This is collaborative coursework. It retains supplied teaching examples, `lib/gui.jar`, bundled documentation and third-party notices. These components are not presented as original portfolio work.

## Included simulations

- Conway's Game of Life and Immigration cellular automata
- Schelling-style segregation
- Single- and multi-ball gravity animations
- Boids and predator–prey swarms

## Architecture

The cellular engine uses toroidal neighborhoods and exposes state metrics. The swarm simulation combines flocking and predator-targeting behaviour with discrete-event scheduling. GUI entry points depend on `lib/gui.jar`.

## Requirements

JDK 21, GNU Make and the tracked `lib/gui.jar` library.

## Build

```bash
make build
```

Compiled classes are written to `bin/`.

## Run

Graphical desktop targets include `make runGameOfLife`, `make runGameOfImmigration`, `make runSegregationSim`, `make runSwarm`, `make runTestBalls`, `make runTestGravityBall` and `make runInvader`. They are not run in headless CI.

## GUI-free validation

After `make build`:

```bash
# Windows
java -ea -cp "bin;lib/gui.jar" cellularSim.CellularEngineTest

# Unix-like systems
java -ea -cp "bin:lib/gui.jar" cellularSim.CellularEngineTest
```

## Documentation

```bash
make javadoc-headless
```

The existing `make javadoc` target also opens the generated index through `xdg-open`.

## Contribution and attribution

History attributes the recent engine refactor, shared scheduler, state metrics and GUI-free cellular regression check to Romain Arzel (`bb2d449`). Earlier swarm work is also associated with a Romain-authored commit (`ab272a8`), while the repository as a whole is multi-author. Supplied teaching examples, `lib/gui.jar`, generated documentation and third-party assets are not claimed as original work.

## Limitations

This is not a production simulation framework or a cybersecurity project. GUI behaviour depends on the supplied library and desktop environment; headless validation covers compilation, one regression check and Javadoc generation only.

## License status

No repository-wide license is added because redistribution rights for the supplied GUI library, teaching material and all collaborative work are not established.
