

public class Node {
    long code;
    byte[] gameBoard;
    int numPegs;

    public Node(byte[] gameBoard) {
        code = 0;
        this.gameBoard = gameBoard;
        generateCode();
    }

    public long getCode() {
        return code;
    }


    public byte[] getGameBoard() {
        return gameBoard;
    }

    //initializes code and ballcount
    //generates unique code for each node based on
    // game board
    private void generateCode() {
        for (byte num : gameBoard) {
            code = code << 1;
            code += num;
            if(num == 1)
            numPegs++;
        }
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
