# Peg-Solitaire-Solver
Solving Peg Solitaire with DFS. I use depth-first search to generate moves and find a solution.

Code can be run in the main method of the RunGame class.

# Notes on the Design
To save memory, I encoded the gameboards into a long, where the last 33 bits of the long each represent whether or not the corresponding slot has a peg in it. I had to use a long because I needed 33 bits, and the next best was an int with 32 bits. At this point, you might be thinking, why didn't you just use an array of 33 booleans? This surprisingly uses more space: a Java array object needs 32 bits to store its address and another 32 to hold its length. This would result in 97 bits per game board as opposed to 64 bits for the long. This means that my encoding algorithm decreases the memory required to solve this problem by ~34%! I may receive a Nobel Peace Prize for this, so if you have trouble contacting me, its probably because I'm in a press conference explaining this accomplishment. See the method generateCode in the Node class for how I did this. I then had to make a method to convert the encoded long into an easily indexed grid representation. This allowed me to efficiently find all of the next possible moves and print out the board state if needed. Basically, I created a system that converts the game board to a low-memory use version for solution storage. Then, I built a function that converts it to a higher memory version to make it easy to display the board and generate the next possible moves. I came up with the idea of encoding board states because my programming had runtime errors from running out of stack space and couldn't reach the solution. I was worried that meant I would have to store paths on my disk, but this fix made that unnecessary.

# Runtime for generating next moves
Because I would have to check every peg position to find the possible moves, the algorithm for generating the next moves could not be done faster than O(n). Consequently, I was okay with adding two O(n) conversions to this process because that would not change the O(n) runtime and save space in my computer's stack. 

# Runtime for testing if two game boards were the same
Encoding my gameboards into longs allowed me to compare two gameboards in O(1) time. This was super handy because I could perform O(1) checks to eliminate redundant paths (path pruning) and save even more memory.

I used map<child code, parent code> to store the paths. The hardest part of the problem was figuring out how to represent the board to store it and search it efficiently. In particular, making an algorithm that can read the board as a human does and then find what possible jumps can be made took a lot of thinking.

