import java.util.*;

public class Game {
    final int boardSize;
    final int centerLocation;
    Node goal;
    Node root;
    Map<Long, Long> nodeMap;
    ArrayList<Node> solutionPath;
    Stack<Long> stackFrontier;
    LinkedList<Node> queueFrontier;
    Map<Long,Boolean> endBoards;
    long rootReached;


    //a map of possible jumps that can be made to any given
    //spot on the
    Map<Integer, ArrayList<Integer>> jumps;

    public Game() {
        rootReached = -1;
        stackFrontier = new Stack<>();
        endBoards = new HashMap<Long, Boolean>();
        queueFrontier = new LinkedList<>();
        boardSize = 33;
        centerLocation = 16;
        solutionPath = new ArrayList<>();
        nodeMap = new HashMap<>();

        goal = new Node(generateGoalNode());

        root = new Node(generateRootNode());

        nodeMap.put(root.getCode(),rootReached );

        jumps = new HashMap<>(33);
        initializeJumps();


    }

    public void playGame() {
        //start at a node
        //generate adjacent nodes
        //add adjacent nodes to stack
        //start again
        //if goal node reached stop
        //generate solution path
        int nodesExpanded = 0;
        stackFrontier.push(root.getCode());

        while (!stackFrontier.isEmpty()) {
            long curr = stackFrontier.pop();
            nodesExpanded++;

            //if goal reached then HALT
            if (curr == goal.getCode()) {
                printResult(nodesExpanded);
                break;
            }

            //otherwise continue to traverse
            exploreAdjacentNodes(curr);

        }


    }

    private void printResult(int nodesExpanded) {
        System.out.println("Solution found. " + nodesExpanded + " nodes expanded... Wow thats alot of nodes man.");
        System.out.println("Here is the solution path: ");
        printSolutionPath();
        System.out.println("DFS failed " + (endBoards.size() - 1) + " times at peg Solitaire Before finding the solution");
        printEndBoards();
    }

    public void printEndBoards() {

        for (long code: endBoards.keySet()) {
            Node node = convertCodeToNode(code);
            if(node.numPegs == 1)
             node.printGameBoard();
        }
    }

    private void printSolutionPath() {
        long currCode = goal.getCode();
        System.out.println("Solution Path: ");
        Stack<Node> solStack = new Stack<>();
        while(currCode != rootReached) {
            solStack.push(convertCodeToNode(currCode));

            currCode = nodeMap.get(currCode);
        }

        while(!solStack.isEmpty()) {
            solStack.pop().printGameBoard();
        }
    }

    //Converts game board code to a Node
    public Node convertCodeToNode(long currCode) {
        Stack<Byte> stack = new Stack<>();
        for(int i = 0; i < boardSize; i++) {
            stack.push((byte) (currCode & 1));
            currCode = currCode >> 1;

        }
        byte[] arr = new byte[boardSize];
        for(int i = 0; i < boardSize; i ++) {
            if(!stack.isEmpty()) {
                arr[i] = stack.pop();
            } else {
                arr[i] = 0;
            }
        }

        return new Node(arr);
    }
    //bug found where I was not appending zeros to gameBoard array so it had empty spots


    /*
     * Generates and adds adjacent nodes to stack
     * */
    public void exploreAdjacentNodes(long currCode) {
        Node node = convertCodeToNode(currCode);
        byte[] currBoard = node.getGameBoard();
        boolean isEndBoard = true;
        for (int i = 0; i < boardSize; i++) {
            //find empty spot on game board
            if (currBoard[i] == 0) {
                //get coordinates of spaces on board 2 away from empty spot
                ArrayList<Integer> currJumps = jumps.get(i);
                //find out if those jumps are possible with current board
                for (int j = 0; j < currJumps.size(); j++) {
                    //if jumps possible then create a new node and add it to stack
                    if (currBoard[currJumps.get(j)] == 1 && currBoard[findNewEmp(i,currJumps.get(j))] == 1 ) {
                        isEndBoard = false;
                        Node adjNode = makeAdjNode(currBoard, i, currJumps.get(j));
                        //add parent to adjNode
                        //check board not already seen
                        if(!nodeMap.containsKey(adjNode.getCode())) {
                            stackFrontier.push(adjNode.getCode());
                            nodeMap.put(adjNode.getCode(), node.getCode());
                        }
                    }
                }

            }
        }

        if(isEndBoard && !endBoards.containsKey(currCode))
            endBoards.put(currCode,true);
    }
    //Bug causing game to keep jumping around infinitely and never reach end
    //


    /*
    parentBoard == game of parent node
    newFill == previously empty spot ball moved to
    newEmp == spot ball was moved from now empty
    We use findNewEmp to calculate the spot that is now a 0
     */
    public Node makeAdjNode(byte[] parentBoard, int newFill, int newEmp) {
        byte[] newBoard = new byte[boardSize];

        for (int i = 0; i < boardSize; i++) {
            newBoard[i] = parentBoard[i];
        }

        newBoard[newEmp] = 0;
        newBoard[newFill] = 1;
        newBoard[findNewEmp(newFill, newEmp)] = 0;

        return new Node(newBoard);
    }


    //returns the index of the ball that was jumped over from newEmp to newFill
    public int findNewEmp(int newFill, int newEmp) {
        //case 1a: horizontal jump from left to right
        if (newFill - newEmp == 2) {
            return newFill - 1;
        }
        //case 1b: horizontal right to left jump
        if (newEmp - newFill == 2) {
            return newEmp - 1;
        }
        //case 2a: vertical jump down
        if (newFill - newEmp > 0) {
            return gridToIndex(indexToGrid(newFill) - 7);
        }
        //case 2b: vertical jump up
        if (newFill - newEmp < 0) {
            return gridToIndex(indexToGrid(newFill) + 7);
        }

        return -69;
    }


    //initializes map of all possible jumps (spaces on board two away orthogonally
    public void initializeJumps() {
        for (int i = 0; i < boardSize; i++) {
            jumps.put(i, generateJumps(i));
        }
    }

    //generate list of all possible jumps to index
    public ArrayList<Integer> generateJumps(int boardIndex) {
        ArrayList<Integer> jumpIndexes = new ArrayList<>();
        int ind = indexToGrid(boardIndex);

        //case 1 index is too close to left wall so has no left jump
        if (ind % 7 == 0 || ind % 7 == 1) {
            jumpIndexes.add(ind - 14);
            jumpIndexes.add(ind + 2);
            jumpIndexes.add(ind + 14);
        }
        //case 2 index is too close to right wall so has no right jump
        else if (ind % 7 == 6 || ind % 7 == 5) {
            jumpIndexes.add(ind - 14);
            jumpIndexes.add(ind - 2);
            jumpIndexes.add(ind + 14);
        }
        //case 3 all jumps can be made
        else {
            jumpIndexes.add(ind - 14);
            jumpIndexes.add(ind - 2);
            jumpIndexes.add(ind + 14);
            jumpIndexes.add(ind + 2);
        }


        jumpIndexes = sanitize(jumpIndexes);
        return jumpIndexes;

    }

    //Convert an index of the game board one of a 7*7 grid numbered
    // with top left == 0 and bottom right == 48
    public int indexToGrid(int index) {
        if (index < 3) {
            return index + 2;
        } else if (index < 6) {
            return index + 6;
        } else if (index < 27) {
            return index + 8;
        } else if (index < 30) {
            return index + 10;
        } else {
            return index + 14;
        }
    }

    //converts grid coords to index on gameboard
    //returns -1 if coords not on game board
    public int gridToIndex(int grid) {
        if (grid < 2) {
            return -1;
        }
        if (grid < 5) {
            return grid - 2;
        } else if (grid < 12 && grid > 8) {
            return grid - 6;
        } else if (grid < 35 && grid > 13) {
            return grid - 8;
        } else if (grid < 40 && grid > 36) {
            return grid - 10;
        } else if (grid < 47 && grid > 43) {
            return grid - 14;
        } else {
            return -1;
        }
    }



    //Array of grid indexes converted to an array of valid game board indexes
    public ArrayList<Integer> sanitize(ArrayList<Integer> arr) {
        for (int i = 0; i < arr.size(); i++) {
            int curr = gridToIndex(arr.get(i));
            if (curr > 33 || curr < 0) {
                arr.remove(i);
                i--;
            } else
                arr.set(i, curr);
        }
        return arr;
    }


    private byte[] generateGoalNode() {
        byte[] goalBoard = new byte[boardSize];
        for (int i = 0; i < boardSize; i++)
            goalBoard[i] = 0;
        goalBoard[16] = 1;
        return goalBoard;
    }

    private byte[] generateRootNode() {
        byte[] rootBoard = new byte[boardSize];
        for (int i = 0; i < boardSize; i++)
            rootBoard[i] = 1;
        rootBoard[16] = 0;
        return rootBoard;
    }


}
