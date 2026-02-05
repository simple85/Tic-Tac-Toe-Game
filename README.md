# Tic-Tac-Toe

[![CI](https://github.com/simple85/Tic-Tac-Toe-Game/actions/workflows/ci.yml/badge.svg)](https://github.com/simple85/Tic-Tac-Toe-Game/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Swing](https://img.shields.io/badge/UI-Java%20Swing-5382a1)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

The classic two-player Tic-Tac-Toe game for the desktop, built with Java Swing.

## Features

- Two players on one computer, X (red) vs O (blue)
- Random starting player each round
- Win detection on all 8 lines, with the winning line highlighted
- Draw detection when the board fills up
- One-click restart

## Tech Stack

| Area | Tools |
|------|-------|
| Language | Java 21 |
| UI | Java Swing |
| Build | Maven, Maven Shade (runnable jar) |
| Testing | JUnit 5 (parameterized tests), AssertJ, JaCoCo |
| CI | GitHub Actions |

## Getting Started

**Prerequisites:** JDK 21+ and Maven 3.9+

```bash
git clone https://github.com/simple85/Tic-Tac-Toe-Game.git
cd Tic-Tac-Toe-Game
mvn package
java -jar target/tic-tac-toe.jar
```

## Design

| Class | Responsibility |
|-------|----------------|
| `Main` | Entry point |
| `TicTacToe` | Swing window, turns and rendering |
| `Board` | Pure game state: placing marks, winning lines, draws |

`Board` has no UI code, so the game rules are verified by unit tests. Every one of the 8 winning lines is tested for both players.

## Project Structure

```text
src/
├── Main.java
├── TicTacToe.java    # Swing UI
└── Board.java        # game rules
test/
└── BoardTest.java
```

## Running Tests

```bash
mvn verify
```

## License

Released under the [MIT License](LICENSE).
