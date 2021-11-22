# Peg-Solitaire-Solver
Solving Peg Solitaire with DFS. I use depth first search to generate every possible game of Peg Solitaire and find solution.

Code can be run in the main method of the RunGame class.

# Notes on the Design
To save stoarage space I encoded the gameboards into a long, where the latter 33 bits of the long each represent wether or not it's corresponding slot has a peg in it. I had to use a long because I needed 33 bits, and the next best was an int with 32 bits. See the method generateCode in the Node class for this. I then had to make a method to convert the long into a grid representaton so that I could find all of the next possible moves, and to print out the board state if needed. Basically I created a system that converts the game board world state to a low memory use version, for storing in the solution map, and then a higher memory version that improved efficiency of displaying the board and gernerating the next possible moves. Because I would have to check every single peg position to find the possible moves, the algorithm could not be done faster than O(n), so I was okay with adding two O(n) conversions to this process because that would not change the O(n) runtime, and would save space in my computers stack. I also could have gone for an array of 33 booleans, that would have used less space than the longs. I used map<child code, parent code> to store the paths. The hardest part of the problem was figuring out how to represent the board such that I could store it and search it efficiently. In particular making an aglorithm that can read the board like a human does and then find what possible jumps can be made was very arduous.



If You don't want run my code but still want to see what it generates, I got you!

** Note - to view this properly formatted you have to copy and paste the following into a text editor.

Solution Path: 
    ___    
   |111|
 __|111|__
|11 111 11|
|11 101 11|
|11 111 11|
 --|111|--
   |111|
    ---
    ___    
   |111|
 __|111|__
|11 111 11|
|11 110 01|
|11 111 11|
 --|111|--
   |111|
    ---
    ___    
   |111|
 __|111|__
|11 111 11|
|11 111 01|
|11 110 11|
 --|110|--
   |111|
    ---
    ___    
   |111|
 __|111|__
|11 111 11|
|11 111 01|
|11 110 11|
 --|001|--
   |111|
    ---
    ___    
   |111|
 __|111|__
|11 111 11|
|11 101 01|
|11 100 11|
 --|011|--
   |111|
    ---
    ___    
   |111|
 __|111|__
|11 111 11|
|11 001 01|
|11 000 11|
 --|111|--
   |111|
    ---
    ___    
   |111|
 __|111|__
|11 111 11|
|11 001 01|
|11 001 00|
 --|111|--
   |111|
    ---
    ___    
   |111|
 __|111|__
|11 111 10|
|11 001 00|
|11 001 01|
 --|111|--
   |111|
    ---
    ___    
   |111|
 __|111|__
|11 111 10|
|11 001 00|
|11 011 01|
 --|101|--
   |101|
    ---
    ___    
   |111|
 __|111|__
|11 111 10|
|11 001 00|
|11 000 11|
 --|101|--
   |101|
    ---
    ___    
   |111|
 __|111|__
|11 111 10|
|11 001 00|
|11 001 00|
 --|101|--
   |101|
    ---
    ___    
   |111|
 __|111|__
|11 111 10|
|11 001 00|
|11 101 00|
 --|001|--
   |001|
    ---
    ___    
   |111|
 __|111|__
|11 111 10|
|11 001 00|
|10 011 00|
 --|001|--
   |001|
    ---
    ___    
   |111|
 __|111|__
|11 111 10|
|11 001 00|
|10 000 10|
 --|001|--
   |001|
    ---
    ___    
   |111|
 __|111|__
|11 111 10|
|11 001 00|
|10 001 10|
 --|000|--
   |000|
    ---
    ___    
   |111|
 __|111|__
|11 111 10|
|11 001 00|
|10 010 00|
 --|000|--
   |000|
    ---
    ___    
   |111|
 __|111|__
|10 111 10|
|10 001 00|
|11 010 00|
 --|000|--
   |000|
    ---
    ___    
   |111|
 __|111|__
|10 111 10|
|10 001 00|
|00 110 00|
 --|000|--
   |000|
    ---
    ___    
   |111|
 __|111|__
|10 111 10|
|10 001 00|
|01 000 00|
 --|000|--
   |000|
    ---
    ___    
   |111|
 __|111|__
|00 111 10|
|00 001 00|
|11 000 00|
 --|000|--
   |000|
    ---
    ___    
   |111|
 __|111|__
|00 111 10|
|00 001 00|
|00 100 00|
 --|000|--
   |000|
    ---
    ___    
   |111|
 __|011|__
|00 011 10|
|00 101 00|
|00 100 00|
 --|000|--
   |000|
    ---
    ___    
   |111|
 __|011|__
|00 100 10|
|00 101 00|
|00 100 00|
 --|000|--
   |000|
    ---
    ___    
   |110|
 __|010|__
|00 101 10|
|00 101 00|
|00 100 00|
 --|000|--
   |000|
    ---
    ___    
   |110|
 __|011|__
|00 100 10|
|00 100 00|
|00 100 00|
 --|000|--
   |000|
    ---
    ___    
   |001|
 __|011|__
|00 100 10|
|00 100 00|
|00 100 00|
 --|000|--
   |000|
    ---
    ___    
   |000|
 __|010|__
|00 101 10|
|00 100 00|
|00 100 00|
 --|000|--
   |000|
    ---
    ___    
   |000|
 __|010|__
|00 110 00|
|00 100 00|
|00 100 00|
 --|000|--
   |000|
    ---
    ___    
   |000|
 __|010|__
|01 000 00|
|00 100 00|
|00 100 00|
 --|000|--
   |000|
    ---
    ___    
   |000|
 __|010|__
|01 100 00|
|00 000 00|
|00 000 00|
 --|000|--
   |000|
    ---
    ___    
   |000|
 __|010|__
|00 010 00|
|00 000 00|
|00 000 00|
 --|000|--
   |000|
    ---
    ___    
   |000|
 __|000|__
|00 000 00|
|00 010 00|
|00 000 00|
 --|000|--
   |000|
    ---
