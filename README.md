# Sudoku

My sedoku project does 3 seprate things.
The primary algorithm used for each stage is "Wave Function Collapse".

## Fill Board
The first stage the ai begins to fill the sudoku board semi randomly using Wave Function Collapse makikng sure not to create an invalid sudoku board.
This Stage will fill the entire board until there is no more empty squares.

## Create puzzle
Once the first stage is complete and the board is completly filled, the ai will immediately start creating the puzzle.
-To create the puzzle the ai attemps to remove tiles at random.
-The ai checks to make sure that the puzzle is still solvable if the tile is removed and also checks to make sure there is still exactly one solution.
-If the puzzle is no longer solvable or there is more than one solution, the ai will decide not to remove the tile and marks it as red.
-If the ai can remove the tile then it marks it as green and removes the tile.
Once all tiles have been marked as either green or red, the puzzle has been created.

## Solve puzzle
Once the puzzle is created, the ai starts to solve the puzzle.
The ai will mark its solution in blue to show its diffrent from the tiles given from the puzzle.
The ai will then use wave function collapse to solve the puzzle backtracking when necessary.
Once the ai solves the puzzle, The ai loops back to a blank board and creates a new puzzle.
