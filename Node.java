import java.util.ArrayList;

public class Node {
    long code;
    byte[] gameBoard;
    ArrayList<Node> adjacentNodes;
    Node parent;


    public Node(byte[] gameBoard) {
        code = 0;

        parent = null;
        this.gameBoard = gameBoard;
        adjacentNodes = new ArrayList<>();
        generateCode();
    }

    public long getCode() {
        return code;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public byte[] getGameBoard() {
        return gameBoard;
    }

    //initializes code and ballcount
    //generates unique code for each node based on
    // game board
    private void generateCode() {
        int i = 0;
        for (long num : gameBoard) {
            code = code << 1;
            code += num;

//            System.out.println(i++);
//            System.out.println(Long.toBinaryString(code));
        }
//        System.out.println(code);
    }


    public void addAdjacentNode(Node adj) {
        adjacentNodes.add(adj);
    }


    public void printGameBoard() {
        System.out.println("    ___    ");
        System.out.printf("   |%d%d%d|\n", gameBoard[0], gameBoard[1], gameBoard[2]);
        System.out.printf(" __|%d%d%d|__\n", gameBoard[3], gameBoard[4], gameBoard[5]);


        System.out.print("|");
        for (int i = 0; i < 7; i++) {
            if (i == 2 || i == 5) {
                System.out.print(" ");
            }
            System.out.printf("%d", gameBoard[6 + i]);
        }
        System.out.println("|");
        System.out.print("|");
        for (int i = 0; i < 7; i++) {
            if (i == 2 || i == 5) {
                System.out.print(" ");
            }
            System.out.printf("%d", gameBoard[13 + i]);
        }
        System.out.println("|");
        System.out.print("|");

        for (int i = 0; i < 7; i++) {
            if (i == 2 || i == 5) {
                System.out.print(" ");
            }
            System.out.printf("%d", gameBoard[20 + i]);
        }
        System.out.println("|");

        System.out.printf(" --|%d%d%d|--\n", gameBoard[27], gameBoard[28], gameBoard[29]);
        System.out.printf("   |%d%d%d|\n", gameBoard[30], gameBoard[31], gameBoard[32]);
        System.out.println("    ---");


    }


}
