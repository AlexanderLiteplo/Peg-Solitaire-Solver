# Peg-Solitaire-Solver
Solving Peg Solitaire with DFS. I use depth first search to generate every possible game of Peg Solitaire and find solution.

Code can be run in the main method of the RunGame class.

# Notes on the Design
To save stoarage space I encoded the gameboards into a long, where the latter 33 bits of the long each represent wether or not it's corresponding slot has a peg in it. I had to use a long because I needed 33 bits, and the next best was an int with 32 bits. See the method generateCode in the Node class for this. I then had to make a method to convert the long into a grid representaton so that I could find all of the next possible moves, and to print out the board state if needed. Basically I created a system that converts the game board world state to a low memory use version, for storing in the solution map, and then a higher memory version that improved efficiency of displaying the board and gernerating the next possible moves. 

Because I would have to check every single peg position to find the possible moves, the algorithm for generating next moves could not be done faster than O(n). Consequently I was okay with adding two O(n) conversions to this process because that would not change the O(n) runtime, and would save space in my computer's stack. To save even more space I also could have gone for an array of 33 booleans.

I used map<child code, parent code> to store the paths. The hardest part of the problem was figuring out how to represent the board such that I could store it and search it efficiently. In particular making an aglorithm that can read the board like a human does and then find what possible jumps can be made was very arduous.

