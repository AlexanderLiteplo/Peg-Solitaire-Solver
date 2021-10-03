import java.util.*;

public class Game {
    final int boardSize;
    final int centerLocation;
    Node goal;
    Node root;
    Map<Long, Boolean> nodeMap;
    ArrayList<Node> solutionPath;
    Stack<Node> stackFrontier;
    LinkedList<Node> queueFrontier;
    ArrayList<Node> endBoards;

    private int[][] grid;

    //a map of possible jumps that can be made to any given
    //spot on the
    Map<Integer, ArrayList<Integer>> jumps;

    public Game() {
        stackFrontier = new Stack<>();
        endBoards = new ArrayList<>();
        queueFrontier = new LinkedList<>();
        boardSize = 33;
        centerLocation = 16;
        solutionPath = new ArrayList<>();
        nodeMap = new HashMap<>();
        System.out.println("making goal");
        goal = new Node(generateGoalNode());
        System.out.println("making root");
        root = new Node(generateRootNode());

        nodeMap.put(root.getCode(), true);

        jumps = new HashMap<>(33);
        initializeJumps();


    }

    public void playGameQueue() {
        //start at a node
        //generate adjacent nodes
        //add adjacent nodes to stack
        //start again
        //if goal node reached stop
        //generate solution path
        int nodesExpanded = 0;
        stackFrontier.push(root);
        queueFrontier.add(root);

        Node lastVisited = null;
        while (!queueFrontier.isEmpty()) {
            Node curr = queueFrontier.pollFirst();
            nodesExpanded++;

            //if goal reached then HALT
            if (curr.code == goal.code) {
                goal = curr;
                System.out.println("Solution found. " + nodesExpanded + " nodes expanded... Wow thats alot of nodes man.");
                printSolutionPath();
                break;
            }

            //otherwise continue to traverse
            exploreAdjacentNodesQueue(curr);
            lastVisited = curr;
        }

        lastVisited.printGameBoard();
        System.out.println("Solution not found. " + nodesExpanded + " Nodes Expanded.");



    }


    public void playGame() {
        //start at a node
        //generate adjacent nodes
        //add adjacent nodes to stack
        //start again
        //if goal node reached stop
        //generate solution path
        int nodesExpanded = 0;
        stackFrontier.push(root);

        Node lastVisited = null;
        while (!stackFrontier.isEmpty()) {
            Node curr = stackFrontier.pop();
            nodesExpanded++;
//            if(nodesExpanded < 20) {
//                curr.printGameBoard();
//            }

            if(nodeMap.size() % 100000 == 0) {
                System.out.println(nodeMap.size());
                curr.printGameBoard();
            }

            //if goal reached then HALT
            if (curr.code == goal.code) {
                goal = curr;
                System.out.println("Solution found. " + nodesExpanded + " nodes expanded... Wow thats alot of nodes man.");
                printSolutionPath();
                break;
            }

            //otherwise continue to traverse
            exploreAdjacentNodes(curr);
            lastVisited = curr;
        }

        lastVisited.printGameBoard();
        System.out.println("Solution not found. " + nodesExpanded + " Nodes Expanded.");



    }

    public void printEndBoards() {
        System.out.println("There are this many endboards: " + endBoards.size());
        for(Node n: endBoards)
            n.printGameBoard();
    }

    private void printSolutionPath() {
        Node curr = goal;
        while(curr.code != root.code) {
            curr.printGameBoard();
            curr = curr.parent;
        }
    }

    /*
     * returns possible jumps to index of current nodes gameboard
     * */
    public void exploreAdjacentNodes(Node node) {
        byte[] currBoard = node.getGameBoard();
        boolean isEndBoard = true;
        for (int i = 0; i < boardSize; i++) {
            //find empty spot on game board
            if (currBoard[i] == 0) {
                //get jumps
                ArrayList<Integer> currJumps = jumps.get(i);
                //find out if those jumps are possible with current board
                for (int j = 0; j < currJumps.size(); j++) {
                    //if jumps possible then create a new node and add it to stack
                    if (currBoard[currJumps.get(j)] == 1) {
                        isEndBoard = false;
                        Node adjNode = makeAdjNode(currBoard, i, currJumps.get(j));
                        //add parent to adjNode

                        //check board not already seen
                        if(!nodeMap.containsKey(adjNode.getCode())) {
                            adjNode.setParent(node);
                            stackFrontier.push(adjNode);
                            nodeMap.put(adjNode.getCode(),true);
                        }
                    }
                }

            }
        }

        if(isEndBoard)
            endBoards.add(node);
    }

    /*
     * returns possible jumps to index of current nodes gameboard
     * */
    public void exploreAdjacentNodesQueue(Node node) {
        byte[] currBoard = node.getGameBoard();
        boolean isEndBoard = true;
        for (int i = 0; i < boardSize; i++) {
            //find empty spot on game board
            if (currBoard[i] == 0) {
                //get jumps
                ArrayList<Integer> currJumps = jumps.get(i);
                //find out if those jumps are possible with current board
                for (int j = 0; j < currJumps.size(); j++) {
                    //if jumps possible then create a new node and add it to stack
                    if (currBoard[currJumps.get(j)] == 1) {
                        isEndBoard = false;
                        Node adjNode = makeAdjNode(currBoard, i, currJumps.get(j));
                        //add parent to adjNode

                        //check board not already seen
                        if(!nodeMap.containsKey(adjNode.getCode())) {
                            adjNode.setParent(node);
                            queueFrontier.add(adjNode);
                            nodeMap.put(adjNode.getCode(),true);
                        }
                    }
                }

            }
        }

        if(isEndBoard)
            endBoards.add(node);
    }

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


    //todo initialize map of possible jumps
    public void initializeJumps() {
        for (int i = 0; i < boardSize; i++) {
            jumps.put(i, generateJumps(i));
        }
    }

    //generate list of all possible jumps to index
    public ArrayList<Integer> generateJumps(int index) {
        ArrayList<Integer> jumpIndexes = new ArrayList<>(4);
        int ind = indexToGrid(index);

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
        } else if (grid < 33 && grid > 13) {
            return grid - 8;
        } else if (grid < 40 && grid > 36) {
            return grid - 10;
        } else if (grid < 47 && grid > 43) {
            return grid - 14;
        } else {
            return -1;
        }
    }


    //todo sanitize an array of all of the 4 possible
    //jumps to only ones that are on the board
    //indexes must be grid indexes
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


    private boolean isGoalNode(Node n) {
        return n.code == goal.code;
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
