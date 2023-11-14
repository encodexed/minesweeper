# Minesweeper for the Java CLI

## Demo & Snippets

Image goes here

---

## How to Play

Rules can be found <a href="https://minesweepergame.com/strategy/how-to-play-minesweeper.php">here</a>. This code is designed to be run in command line interface and you will be prompted to enter a command each turn.

### Commands

Here are the accepted command patterns:

- `new` : Begins a new game.
- `quit` : Quits the application.
- `help` : Displays the available list of commands.
- `a9` : Opens the cell at coordinate A9
- `!c3` : Flags the cell at coordinate C3
- `time` : Displays the current time taken

---

## Requirements / Purpose

Minesweeper as a Java application is a project built as part of my participation during the \_nology bootcamp. Were given about 10 days to recreate Microsoft's legendary game to be run in a terminal. The purpose of this project was to help us practice our skills with Java which we had recently learnt.

---

## Build Steps

To run this project in your local environment, paste the following into your terminal:

```bash
git clone git@github.com:encodexed/minesweeper.git
cd minesweeper/bin
java Main
```

---

## Design Goals / Approach

- Working with Java was an interesting experience coming from JavaScript as there was a lot more planning and design choices to be considered to think about the composition and structure of the code and its classes. Here are some of the classes I wrote and their general purpose:
  - `Game`: Contains properties and methods relevant to the game state and statistics, like whether the game is over/running, if it's the first turn and how long the game has been running.
  - `Grid`: Contains properties and methods relevant to the grid's state, including the its dimensions, mine and flag locations.
  - `Tile`: Contains properties and methods relevant to individual tiles, such as their coordinates on the grid and whether they are revealed, concealed or flagged.
  - `CommandUtils`: Contains mostly static methods for handling commands from the player, performing basic logic operations and type conversions.

---

## Features

This projects is capable of handling almost everything that is possible in the original version of the game, such as:

- Flagging or revealing tiles
- Keeping track of time taken and flags used
- Creating a new game whenever the player wants
- Revealing a cascading amount of tiles if a player chooses to reveal a tile with zero nearby mines

---

## Known issues

None, as far as I know.

---

## Future Goals

This project is wrapped up and I will likely not be working on it anymore, instead focusing on new projects of greater learning potential. If I were to dedicate time to this project, I would:

- Write tests for the methods to ensure they work now and into the future
- Implement a Graphical User Interface (GUI) to improve the user interface and experience for players
- Make it possible to handle other sizes of grids besides 10x10

---

## Licensing Details

Copyright 2023 Robert Gollan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without limitation in the rights to use, copy, modify, merge, publish, and/ or distribute copies of the Software in an educational or personal context, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
Permission is granted to sell and/ or distribute copies of the Software in a commercial context, subject to the following conditions:

Substantial changes: adding, removing, or modifying large parts, shall be developed in the Software. Reorganizing logic in the software does not warrant a substantial change.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
